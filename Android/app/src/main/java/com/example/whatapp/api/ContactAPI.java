package com.example.whatapp.api;

import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.whatapp.App;
import com.example.whatapp.R;
import com.example.whatapp.database.ContactDao;
import com.example.whatapp.database.LocalDatabase;
import com.example.whatapp.entities.Contact;
import com.example.whatapp.entities.Invitation;
import com.example.whatapp.entities.Lambda;
import com.example.whatapp.entities.LoginInfo;
import com.example.whatapp.entities.NewContact;
import com.example.whatapp.repositories.ContactsRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ContactAPI {
    private Retrofit retrofit;
    private WebServiceAPI webServiceAPI;
    private ContactDao contactDao;

    public ContactAPI() {
        contactDao = Room.databaseBuilder(App.getContext(), LocalDatabase.class, "DB")
                    .allowMainThreadQueries()
                    .build().contactDao();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(App.getContext().getString(R.string.BaseUrl))
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }

    /*
     function gets all of the contacts with the current token from the WebAPI
     and sets the response in contacts
     */
    public void getAllContacts(String userId, String token, MutableLiveData<List<Contact>> contacts) {
        Call<List<Contact>> call;
        call = webServiceAPI.getAllContacts(token);
        call.enqueue(new Callback<List<Contact>>() {
            @Override
            public void onResponse(Call<List<Contact>> call, Response<List<Contact>> response) {
                if (response.code() == 200) {
                    // insert all of the contacts to the localDB in separate thread
                    new Thread(() -> {
                        for (Contact contact : response.body()) {
                            contact.setUserId(userId);
                            contactDao.insert(contact);
                        }
                    });
                    contacts.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Contact>> call, Throwable t) {
            }
        });
    }

    public void add(String from, String to,String nickname, String server, ContactsRepository repository) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        String serverNumber = server.split(":")[1];
        String urlContact = (App.getContext().getString(R.string.BaseUrl)).replace("5028", serverNumber);
        Retrofit contactRetrofit = new Retrofit.Builder()
                .baseUrl(urlContact)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        WebServiceAPI contactWebServiceAPI = contactRetrofit.create(WebServiceAPI.class);
        Call<String> callContact;
        Invitation invitation = new Invitation(from,to,"localhost:5028");
        callContact = contactWebServiceAPI.invite(invitation);
        callContact.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.code() == 201) {
                    Call<String> callUser;
                    callUser = webServiceAPI.createContact(new NewContact(to,nickname,server));
                    callUser.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call2, Response<String> response2) {
                            if (response2.code() == 201) {
                                contactDao.insert(new Contact(to, from,  to, server, "", 0, ""));
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });
    }
}

package com.example.whatapp.api;

import android.media.Image;

import androidx.lifecycle.LiveData;
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

import java.util.Calendar;
import java.util.Formatter;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ContactAPI {
    private static String serviceUrl = App.getContext().getString(R.string.BaseUrl)
                                        .replace("localhost", "10.0.2.2");
    private static Gson gson = new GsonBuilder().setLenient().create();
    private Retrofit retrofit;
    private WebServiceAPI webServiceAPI;
    private ContactDao contactDao;

    public ContactAPI() {
        contactDao = Room.databaseBuilder(App.getContext(), LocalDatabase.class, "DB")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build().contactDao();
        retrofit = new Retrofit.Builder()
                .baseUrl(serviceUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }

    /**
     * function gets the contacts list from the webservice
     * and sets it's value on the mutable live data
     * @param userId the user id
     * @param token the JWT token
     * @param contacts the contacts list
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

    /**
     * function adds a new contact to the DB through the service api
     * @param userId the user id
     * @param contactId the new contact id
     * @param contactName the new contact name
     * @param server the new contact server
     * @param token the user id JWT token
     * @param contacts the mutable contacts list
     */
    public void add(String userId, String contactId, String contactName, String server, String token, MutableLiveData<List<Contact>> contacts) {
        String url = ("http://" + server + "/api/").replace("localhost", "10.0.2.2");
        Retrofit contactRetrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        WebServiceAPI contactWebServiceAPI = contactRetrofit.create(WebServiceAPI.class);
        Call<String> callContact;
        Invitation invitation = new Invitation(userId,contactId,App.getContext().getString(R.string.web_api_url));
        callContact = contactWebServiceAPI.invite(invitation);
        callContact.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.code() == 201) {
                    Contact c = new Contact(contactId, userId,  contactName, server, "", 0, "");
                    addNewContact(c, token, contacts);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });
    }

    private void addNewContact(Contact contact, String token, MutableLiveData<List<Contact>> contacts) {
        Call<String> callUser;
        NewContact newContact = new NewContact(contact.getId(),contact.getName(),contact.getServer());
        callUser = webServiceAPI.createContact(token, newContact);
        callUser.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.code() == 201) {
                    String lastDate = new Formatter()
                            .format("%tl:%tM", Calendar.getInstance(), Calendar.getInstance())
                            .toString();
                    contact.setLastDate(lastDate);
                    contactDao.insert(contact);
                    contacts.setValue(contactDao.getAllContacts(contact.getUserId()));
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });
    }
}

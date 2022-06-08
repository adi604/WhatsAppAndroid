package com.example.whatapp.api;

import androidx.lifecycle.MutableLiveData;

import com.example.whatapp.App;
import com.example.whatapp.R;
import com.example.whatapp.entities.Contact;
import com.example.whatapp.entities.Lambda;
import com.example.whatapp.entities.LoginInfo;
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

    public ContactAPI() {
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
    public void getAllContacts(String token, MutableLiveData<List<Contact>> contacts) {
        Call<List<Contact>> call;
        call = webServiceAPI.getAllContacts(token);
        call.enqueue(new Callback<List<Contact>>() {
            @Override
            public void onResponse(Call<List<Contact>> call, Response<List<Contact>> response) {
                contacts.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Contact>> call, Throwable t) {
            }
        });
    }
}

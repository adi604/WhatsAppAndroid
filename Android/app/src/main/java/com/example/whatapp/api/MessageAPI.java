package com.example.whatapp.api;

import androidx.lifecycle.MutableLiveData;

import com.example.whatapp.App;
import com.example.whatapp.R;
import com.example.whatapp.entities.Contact;
import com.example.whatapp.entities.Message;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MessageAPI {
    private Retrofit retrofit;
    private WebServiceAPI webServiceAPI;
    private String contact;

    public MessageAPI(String contact) {
        this.contact = contact;
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        String url = "http://10.0.2.2:5028/api/contacts/" + contact + "/";
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
    }

    public void getAllMessages(String token, MutableLiveData<List<Message>> messages) {
        Call<List<Message>> call;
        call = webServiceAPI.getMessages(token,contact);
        call.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                if (response.code() == 200) {
                    // insert all of the messages to the localDB in separate thread
                    new Thread(() -> {
                        for (Message message : response.body()) {
                        }
                    });
                    messages.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
            }
        });
    }
}

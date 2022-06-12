package com.example.whatapp.api;

import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.whatapp.App;
import com.example.whatapp.R;
import com.example.whatapp.database.LocalDatabase;
import com.example.whatapp.database.MessageDao;
import com.example.whatapp.entities.Contact;
import com.example.whatapp.entities.Message;
import com.example.whatapp.entities.NewUpdateMessage;
import com.example.whatapp.entities.Transfer;
import com.example.whatapp.entities.User;
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

public class MessageAPI {
    private static String serviceUrl = App.getContext().getString(R.string.BaseUrl)
            .replace("localhost", "10.0.2.2");
    private static Gson gson = new GsonBuilder().setLenient().create();
    private Retrofit retrofit;
    private WebServiceAPI webServiceAPI;
    private User user;
    private Contact contact;
    private MessageDao messageDao;

    public MessageAPI(User user, Contact contact) {
        this.user = user;
        this.contact = contact;
        LocalDatabase db = Room.databaseBuilder(App.getContext(), LocalDatabase.class, "DB")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
        //db.clearAllTables(); //****************************************8
        this.messageDao = db.messageDao();
        this.retrofit = new Retrofit.Builder()
                .baseUrl(serviceUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        this.webServiceAPI = retrofit.create(WebServiceAPI.class);
    }

    /**
     * function gets all of the messages send between the contact and the user that logged in
     *
     * @param token    the JWT token
     * @param messages the MutableliveData to set the response to
     */
    public void getAllMessages(String token, MutableLiveData<List<Message>> messages) {
        Call<List<Message>> call;
        call = webServiceAPI.getAllMessages(token, this.contact.getId());
        call.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                if (response.code() == 200) {
                    // insert all of the messages to the localDB in separate thread
                    for (Message message : response.body()) {
                        message.setContactId(contact.getId());
                        message.setUserId(user.getId());
                        messageDao.insert(message);
                    }
                    messages.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
            }
        });
    }

    /**
     * function send a new message to the web service
     *
     * @param token    the jwt token
     * @param content  the content of the new message
     * @param messages the list of all messages
     */
    public void sendMessage(String token, String content, MutableLiveData<List<Message>> messages) {
        Call<String> call;
        NewUpdateMessage newMessage = new NewUpdateMessage(content);
        call = webServiceAPI.sendMessage(token, this.contact.getId(), newMessage);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.code() == 201) {
                    sendTransfer(content, messages);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });
    }

    /**
     * function send a new transfer to the web api
     *
     * @param content  the content of the message
     * @param messages the messages list to add the new message to
     */
    public void sendTransfer(String content, MutableLiveData<List<Message>> messages) {
        Call<String> call;
        String url = ("http://" + contact.getServer() + "/api/").replace("localhost", "10.0.2.2");
        Retrofit contactRetrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        WebServiceAPI contactWebServiceAPI = contactRetrofit.create(WebServiceAPI.class);
        call = contactWebServiceAPI.sendTransfer(new Transfer(user.getId(), contact.getId(), content));
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.code() == 201) {
                    Formatter fmt = new Formatter();
                    fmt.format("%tl:%tM", Calendar.getInstance(), Calendar.getInstance());
                    String time = fmt.toString();
                    Message m = new Message(user.getId(), contact.getId(), true, content, time);
                    messageDao.insert(m);
                    messages.setValue(messageDao.getAllMessages(user.getId(), contact.getId()));
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
            }
        });
    }
}

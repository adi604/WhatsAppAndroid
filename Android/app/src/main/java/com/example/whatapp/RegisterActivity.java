package com.example.whatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.whatapp.api.UserAPI;
import com.example.whatapp.entities.Lambda;
import com.example.whatapp.entities.User;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class RegisterActivity extends AppCompatActivity {

    private String img = "";
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        UserAPI userAPI = new UserAPI();

        Button btnSignIn = findViewById(R.id.btnSignIn);
        btnSignIn.setOnClickListener(v -> {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        });

        Button btnSignUp = findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(v -> {
            // validate all of the fields entered
            User user = getAndValidateUser();
            if(user == null) {
                return;
            }
            // creating a new user
            userAPI.register(user, new Lambda() {
                // function that will be called on response from the webAPI
                @Override
                public void onLoginResponse(String response) {
                    // if the username isnt taken than gets the token and start the contact activity
                    if (!response.equals("Username already taken")) {
                        Intent i = new Intent(RegisterActivity.this, ContactsActivity.class);
                        String token = "Bearer " + response;
                        i.putExtra("token", token);
                        i.putExtra("current-user", new Gson().toJson(response));
                        startActivity(i);
                        return;
                    }
                    Toast.makeText(RegisterActivity.this, R.string.register_username_taken_msg, Toast.LENGTH_SHORT).show();
                }
            });
        });

        Button btnUploadImage = findViewById(R.id.btnUploadImage);
        btnUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                galleryIntent();
            }
        });
    }


    public void galleryIntent(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 100);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == 100) {
                // Get the url of the image from data
                Uri imgUri = data.getData();
                try {
                    InputStream inputStream = getContentResolver().openInputStream(imgUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    img = encodeImage(bitmap);
                }
                catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String encodeImage(Bitmap bitmap) {
        int width = 150;
        int height = bitmap.getHeight() * width / bitmap.getWidth();
        Bitmap preBitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        preBitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }


    // function tests if the fields are valid and if they are then return a new user with those fields
    private User getAndValidateUser() {
        EditText etUsername = findViewById(R.id.etRegisterUsername);
        String username = etUsername.getText().toString();
        EditText etNickname = findViewById(R.id.etRegisterNickname);
        String nickname = etNickname.getText().toString();
        EditText etPassword = findViewById(R.id.etRegisterPassword);
        String password = etPassword.getText().toString();
        EditText etConfPass = findViewById(R.id.etRegisterConfirmPassword);
        String confPassword = etConfPass.getText().toString();

        if (username.isEmpty() || nickname.isEmpty() || password.isEmpty() || confPassword.isEmpty() || img.isEmpty()) {
            Toast.makeText(RegisterActivity.this, R.string.register_empty_field_msg , Toast.LENGTH_SHORT).show();
            return null;
        } else if (!password.equals(confPassword)) {
            Toast.makeText(RegisterActivity.this, R.string.register_password_confirmation_msg , Toast.LENGTH_SHORT).show();
            return null;
        } else if (password.length() < 10) {
            Toast.makeText(RegisterActivity.this, R.string.register_password_length_msg , Toast.LENGTH_SHORT).show();
            return null;
        }
        return new User(username, nickname, password, img);
    }
}
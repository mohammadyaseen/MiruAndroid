package com.entertechsolutions.miruandroid.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.entertechsolutions.miruandroid.Models.LoginModel;
import com.entertechsolutions.miruandroid.Models.LoginResponce;
import com.entertechsolutions.miruandroid.R;
import com.entertechsolutions.miruandroid.Storage.SharedPreffManager;
import com.entertechsolutions.miruandroid.Utils.Constant;
import com.entertechsolutions.miruandroid.Utils.ServiceUtils;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Circle;
import com.google.gson.JsonObject;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    Button back_btn ,login;
    EditText pass, email;
    TextView register, fotgotpass;
    ProgressBar progressBar;
    android.app.AlertDialog waitingDialog;
    LoginModel login_data_model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login=findViewById(R.id.login);

        waitingDialog = new SpotsDialog.Builder()
                .setContext(Login.this)
                .setTheme(R.style.Custom)
                .setCancelable(false)
                .build();

        back_btn = findViewById(R.id.back_btn_2);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        pass = findViewById(R.id.password);
        email = findViewById(R.id.email);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserLogin();

            }
        });

    }

    public void  UserLogin(){
        String phoneno = email.getText().toString().trim();
        String password = pass.getText().toString().trim();

        if (phoneno.isEmpty()) {
            email.setError("Please Enter Email");
            email.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            pass.setError("Please Enter Password");
            pass.requestFocus();
            return;
        }
        // progressBar.setVisibility(View.VISIBLE);

        waitingDialog.show();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("email", phoneno);
        jsonObject.addProperty("password", password);

        ServiceUtils.api.Login(jsonObject)
                .enqueue(new Callback<LoginResponce>() {
                    @Override
                    public void onResponse(@NonNull Call<LoginResponce> call, @NonNull Response<LoginResponce> response) {
                        //progressBar.setVisibility(View.GONE);
                        LoginResponce loginResponse = response.body();
                        waitingDialog.hide();
                        if (response.isSuccessful() && response.body() != null) {
                            assert loginResponse != null;
                            if (loginResponse.getIsSuccess()) {

                                login_data_model = loginResponse.getData();

                                Log.e("response","Name   "+login_data_model.getName());
                                // Toast.makeText(Login.this, login_data_model.getUser().getName(), Toast.LENGTH_LONG).show();
                                //token = loginResponse.getData().getToken();
                                //updateToken(token);
                                login_data_model.setAuthToken(loginResponse.getToken());
                                SharedPreffManager.getInstance(Login.this).saveUser(loginResponse.getData());
                                Intent it = new Intent(Login.this, MainActivity.class);
                                // it.putExtra("num", phoneNo);
                                startActivity(it);
                                finish();
                            } else {
                                Toast.makeText(Login.this, loginResponse.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }

                    }

                    @Override
                    public void onFailure(@NonNull Call<LoginResponce> call, Throwable t) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(Login.this, "Something Went Wrong Please Try Again", Toast.LENGTH_SHORT).show();
                        waitingDialog.hide();

                    }
                });
    }



}

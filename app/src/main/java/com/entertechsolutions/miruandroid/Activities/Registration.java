package com.entertechsolutions.miruandroid.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.entertechsolutions.miruandroid.Models.SignUpResponce;
import com.entertechsolutions.miruandroid.R;
import com.entertechsolutions.miruandroid.Utils.Constant;
import com.entertechsolutions.miruandroid.Utils.ServiceUtils;
import com.google.gson.JsonObject;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Registration extends AppCompatActivity {


    EditText firstname,phone_no, emailaddres, password;
    Button back_btn,sign_up;
    android.app.AlertDialog waitingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        back_btn = findViewById(R.id.back_btn_s);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        sign_up = findViewById(R.id.user_add);
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(Registration.this, VerifyCode.class);
                //waitingDialog.hide();
                startActivity(intent);*/
                user_signup();
            }
        });

        waitingDialog = new SpotsDialog.Builder()
                .setContext(Registration.this)
                .setTheme(R.style.Custom)
                .setCancelable(false)
                .build();

        firstname = findViewById(R.id.fname);
        phone_no = findViewById(R.id.phone_no);
        emailaddres = findViewById(R.id.s_email);
        password = findViewById(R.id.s_password);

    }


    private void user_signup() {

        String Fname = firstname.getText().toString().trim();
        String Phone = phone_no.getText().toString().trim();
        String email = emailaddres.getText().toString().trim();
        String pass = password.getText().toString().trim();

        if (Fname.isEmpty()) {
            firstname.setError("FirstName Is Required");
            firstname.requestFocus();
            return;
        }

        if (Phone.isEmpty()) {
            phone_no.setError("Phone Number Is Required");
            phone_no.requestFocus();
            return;
        }


        if (email.isEmpty()) {
            emailaddres.setError("Email Is Required");
            emailaddres.requestFocus();
            return;
        }

        if (pass.isEmpty()) {
            password.setError("Password Is Required");
            password.requestFocus();
            return;
        }

        if (pass.length()<6){
            password.setError("Password Should Be Atleast 6 Character Long");
            password.requestFocus();
            return;

        }

        waitingDialog.show();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", Fname);
        jsonObject.addProperty("phoneNo", Phone);
        jsonObject.addProperty("email", email);
        jsonObject.addProperty("password", pass);
        jsonObject.addProperty("isMobileCall", true);

        Log.e("onResponse ", " " + jsonObject);
        ServiceUtils.api.register(jsonObject)
                .enqueue(new Callback<SignUpResponce>() {
                    @Override
                    public void onResponse(retrofit2.Call<SignUpResponce> call, Response<SignUpResponce> response) {
                        // progressBar.setVisibility(View.GONE);
                        waitingDialog.hide();
                        SignUpResponce loginResponse = response.body();
                        // Log.e("onResponse ", " " + loginResponse.getError());

                        if (response.isSuccessful() && response.body() != null) {
                            assert loginResponse != null;
                            Log.e("onResponse ", " " + response);
                            Log.e("data", "main  " + loginResponse.getCode());
                            if (loginResponse.getIsSuccess()) {
                                Toast.makeText(Registration.this, "Profile Updated", Toast.LENGTH_LONG).show();
                                //loginResponse.ge().setAuthToken(userToken);
                                Intent it = new Intent(Registration.this, VerifyCode.class);
                                it.putExtra("email", email);
                                //it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                //overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                                startActivity(it);
                                Log.e("data", "Token  " + loginResponse.getCode());
                                 Toast.makeText(Registration.this,loginResponse.getCode(),Toast.LENGTH_LONG).show();
                       /* Intent it = new Intent(Registration_one.this, Registration_Two.class);
                        startActivity(it);*/
                            } else {
                                Toast.makeText(Registration.this, loginResponse.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                    @Override
                    public void onFailure(retrofit2.Call<SignUpResponce> call, Throwable t) {
                        //progressBar.setVisibility(View.GONE);
                        waitingDialog.hide();
                        Toast.makeText(Registration.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
       /* Intent it = new Intent(Verify_OTP.this, Registration_one.class);
        startActivity(it);
        finish();*/

    }

    }



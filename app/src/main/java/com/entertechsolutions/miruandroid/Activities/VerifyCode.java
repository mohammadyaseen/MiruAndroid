package com.entertechsolutions.miruandroid.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.entertechsolutions.miruandroid.Models.LoginModel;
import com.entertechsolutions.miruandroid.Models.LoginResponce;
import com.entertechsolutions.miruandroid.Models.SignUpResponce;
import com.entertechsolutions.miruandroid.Models.VerifyModel;
import com.entertechsolutions.miruandroid.MyApplication;
import com.entertechsolutions.miruandroid.R;
import com.entertechsolutions.miruandroid.Storage.SharedPreffManager;
import com.entertechsolutions.miruandroid.Utils.Functions;
import com.entertechsolutions.miruandroid.Utils.ServiceUtils;
import com.google.gson.JsonObject;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyCode extends AppCompatActivity {

    Button back_btn,next;
    EditText codetext;
    android.app.AlertDialog waitingDialog;
    String email,password,code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_code);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        password = intent.getStringExtra("pass");
        code = intent.getStringExtra("code");
        Log.e("Id   "," " + email);


        back_btn = findViewById(R.id.backBtnVerify);
        back_btn.setOnClickListener(v -> onBackPressed());

        next = findViewById(R.id.verifyBtn);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent = new Intent(VerifyCode.this, ChildList.class);
                //waitingDialog.hide();
                startActivity(intent);*/

               user_signup();
            }
        });

        waitingDialog = new SpotsDialog.Builder()
                .setContext(VerifyCode.this)
                .setTheme(R.style.Custom)
                .setCancelable(false)
                .build();

        codetext = findViewById(R.id.numberVerify);

        codetext.setText(code);

    }


    private void user_signup() {

        codetext.setText(code);
        String Fname = codetext.getText().toString().trim();



        if (Fname.isEmpty()) {
            codetext.setError("Code Is Required");
            codetext.requestFocus();
            return;
        }

        if (Fname.length()<4){
            codetext.setError("Code Should Be At least 4 Character Long");
            codetext.requestFocus();
            return;

        }

        waitingDialog.show();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("verificationCode", Fname);
        jsonObject.addProperty("email", email);
        jsonObject.addProperty("isMobileCall", true);

        Log.e("onResponse ", " " + jsonObject);
        ServiceUtils.api.getcode(jsonObject)
                .enqueue(new Callback<VerifyModel>() {
                    @Override
                    public void onResponse(retrofit2.Call<VerifyModel> call, Response<VerifyModel> response) {
                        // progressBar.setVisibility(View.GONE);
                        waitingDialog.hide();
                        VerifyModel loginResponse = response.body();
                        // Log.e("onResponse ", " " + loginResponse.getError());

                        if (response.isSuccessful() && response.body() != null) {
                            assert loginResponse != null;
                            Log.e("onResponse ", " " + response);
                            Log.e("data", "main  " + loginResponse.getMessage());
                            if (loginResponse.getIsSuccess()) {
                               // Toast.makeText(VerifyCode.this, "", Toast.LENGTH_LONG).show();
                                //loginResponse.ge().setAuthToken(userToken);
                                UserLogin(email,password);


                                Log.e("data", "Token  " + loginResponse.getMessage());
                                Toast.makeText(VerifyCode.this,loginResponse.getMessage(),Toast.LENGTH_LONG).show();
                                Toast.makeText(VerifyCode.this,"Please Login your Account",Toast.LENGTH_LONG).show();
                       /* Intent it = new Intent(Registration_one.this, Registration_Two.class);
                        startActivity(it);*/
                            } else {
                                Toast.makeText(VerifyCode.this, loginResponse.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                    @Override
                    public void onFailure(retrofit2.Call<VerifyModel> call, Throwable t) {
                        //progressBar.setVisibility(View.GONE);
                        waitingDialog.hide();
                        Toast.makeText(VerifyCode.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
       /* Intent it = new Intent(Verify_OTP.this, Registration_one.class);
        startActivity(it);
        finish();*/

    }


    public  void  UserLogin(String phoneno, String password){

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
                        if (response.isSuccessful() && response.body() != null) {
                            assert loginResponse != null;
                            if (loginResponse.getIsSuccess()) {

                                LoginModel loginResponce = loginResponse.getData();

                                waitingDialog.hide();
                                Log.e("response","Name   "+loginResponce.getName());
                                // Toast.makeText(Login.this, login_data_model.getUser().getName(), Toast.LENGTH_LONG).show();
                                //token = loginResponse.getData().getToken();
                                //updateToken(token);
                                loginResponce.setAuthToken(loginResponse.getToken());
                                SharedPreffManager.getInstance(MyApplication.getContext()).saveUser(loginResponse.getData());
                                Intent it = new Intent(VerifyCode.this, ChildList.class);
                                it.putExtra("email", email);
                                it.putExtra("verify",0);
                                //it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                //overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                                startActivity(it);

                            } else {
                                waitingDialog.hide();
                                Toast.makeText(MyApplication.getContext(), loginResponse.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }

                    }

                    @Override
                    public void onFailure(@NonNull Call<LoginResponce> call, Throwable t) {
                        waitingDialog.hide();
                        Toast.makeText(MyApplication.getContext(), "Something Went Wrong Please Try Again", Toast.LENGTH_SHORT).show();


                    }
                });
    }

}

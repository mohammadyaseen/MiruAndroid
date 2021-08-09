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
import com.entertechsolutions.miruandroid.Models.VerifyModel;
import com.entertechsolutions.miruandroid.R;
import com.entertechsolutions.miruandroid.Utils.ServiceUtils;
import com.google.gson.JsonObject;

import dmax.dialog.SpotsDialog;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyCode extends AppCompatActivity {

    Button back_btn,next;
    EditText codetext;
    android.app.AlertDialog waitingDialog;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_code);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");
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

    }


    private void user_signup() {

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
                                Intent it = new Intent(VerifyCode.this, Login.class);
                                it.putExtra("email", email);
                                it.putExtra("verify",0);
                                //it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                //overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                                startActivity(it);
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


}

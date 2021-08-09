package com.entertechsolutions.miruandroid.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.entertechsolutions.miruandroid.R;

public class RegisterChild extends AppCompatActivity {

    Button back_btn,sign_up;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_child);

        back_btn = findViewById(R.id.backBtnAddChild);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        sign_up = findViewById(R.id.submitChild);
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // user_signup();
                Intent intent = new Intent(RegisterChild.this, MainActivity.class);
                //waitingDialog.hide();
                startActivity(intent);
            }
        });
    }
}

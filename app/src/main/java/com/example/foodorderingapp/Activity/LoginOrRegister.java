package com.example.foodorderingapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodorderingapp.R;

public class LoginOrRegister extends AppCompatActivity {

    TextView SignWithEmail, SignWithPhone, SignUp;
    ImageView bgImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_or_register);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        SignWithEmail= (TextView)findViewById(R.id.SignWithEmail);
        SignWithPhone= (TextView)findViewById(R.id.SignWithPhone);
        SignUp= (TextView)findViewById(R.id.LogIn);

        SignWithEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signEmail = new Intent(LoginOrRegister.this, LoginEmail.class);
                signEmail.putExtra("Home", "Email");
                startActivity(signEmail);
                finish();
            }
        });

        SignWithPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signPhone = new Intent(LoginOrRegister.this, LoginPhone.class);
                signPhone.putExtra("Home", "Phone");
                startActivity(signPhone);
                finish();
            }
        });

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signUp = new Intent(LoginOrRegister.this, SignUpPage.class);
                signUp.putExtra("Home", "SignUp");
                startActivity(signUp);
                finish();
            }
        });
    }
}
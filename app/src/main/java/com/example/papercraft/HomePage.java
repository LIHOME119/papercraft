package com.example.papercraft;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;

public class HomePage extends AppCompatActivity {

    TextView homeLoginName_tv, homeLoginMail_tv;
    Button logout_btn, map_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        homeLoginMail_tv=findViewById(R.id.tv_homeLoginMail);
        homeLoginName_tv=findViewById(R.id.tv_homeLoginName);
        logout_btn=findViewById(R.id.btn_logout);
        map_btn=findViewById(R.id.btn_map);
        map_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MapsActivity.class);
                startActivity(intent);
            }
        });
        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if(signInAccount != null){
            homeLoginName_tv.setText(signInAccount.getDisplayName());
            homeLoginMail_tv.setText(signInAccount.getEmail());
        }
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
//                Intent intent = new Intent(getApplicationContext(),LoginPage.class);
//                startActivity(intent);
                finish();
            }
        });
    }
}
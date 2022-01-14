package com.example.papercraft;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.concurrent.Executor;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class HomePage extends AppCompatActivity {

    TextView homeLoginName_tv, homeLoginMail_tv;
    public static DatabaseReference dataRef= FirebaseDatabase.getInstance().getReference();
    public static String user;


    ExtendedFloatingActionButton logout_fbtn, shop_fbtn, custom_fbtn, info_fbtn;
    FloatingActionButton map_fbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        homeLoginMail_tv=findViewById(R.id.tv_homeLoginMail);
        homeLoginName_tv=findViewById(R.id.tv_homeLoginName);
        custom_fbtn=findViewById(R.id.fbtn_custom);

        GifImageView imageView1=findViewById(R.id.home_background);
        try {
            GifDrawable gifDrawable = new GifDrawable(getResources(), R.drawable.homeback3);
            imageView1.setImageDrawable(gifDrawable);
        } catch (IOException e) {
            e.printStackTrace();
        }
        custom_fbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), customPaperCraft.class);
                startActivity(intent);
            }
        });
        info_fbtn=findViewById(R.id.fbtn_fingerprint);
        map_fbtn=findViewById(R.id.fbtn_map);
        shop_fbtn=findViewById(R.id.fbtn_shop);
        shop_fbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ShopPage.class);
                startActivity(intent);
            }
        });
        logout_fbtn=findViewById(R.id.fbtn_logout);


        //fingerprint

        info_fbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BiometricPrompt.PromptInfo promptInfo= new BiometricPrompt.PromptInfo.Builder()
                        .setTitle("Please Verify to view personal information")
                        .setDescription("User Authentication is required to proceed")
                        .setNegativeButtonText("Cancel")
                        .build();

                getPrompt().authenticate(promptInfo);
            }
        });
        //google map
        map_fbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MapsActivity.class);
                startActivity(intent);
            }
        });
        //google name and mail
        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if(signInAccount != null){
            homeLoginName_tv.setText(signInAccount.getDisplayName());
            homeLoginMail_tv.setText(signInAccount.getEmail());
            user=signInAccount.getDisplayName();
            DatabaseReference namereference= dataRef.child("users").child(signInAccount.getDisplayName()).child("username");
            DatabaseReference mailreference= dataRef.child("users").child(signInAccount.getDisplayName()).child("email");
            namereference.setValue(signInAccount.getDisplayName());
            mailreference.setValue(signInAccount.getEmail());





        }
//        logout
        logout_fbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (signInAccount!=null){
                    FirebaseAuth.getInstance().signOut();
                    finish();
                }else {
                    finish();
                }

//                Intent intent = new Intent(getApplicationContext(),LoginPage.class);
//                startActivity(intent);

            }
        });


//        logout_fbtn.shir

    }
//fingerpirnt
    private BiometricPrompt getPrompt(){
        Executor executor = ContextCompat.getMainExecutor(this);
        BiometricPrompt.AuthenticationCallback callback = new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                notifyUser(errString.toString());
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                notifyUser("Authentication Succeeded!!");
                Intent intent = new Intent(HomePage.this, Info.class);
                startActivity(intent);
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                notifyUser("Authentication Failed");
            }
        };

        BiometricPrompt biometricPrompt = new BiometricPrompt(this, executor, callback);
        return biometricPrompt;

    }

    private void notifyUser(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}
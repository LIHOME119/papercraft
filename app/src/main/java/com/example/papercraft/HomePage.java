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

import java.util.concurrent.Executor;

public class HomePage extends AppCompatActivity {

    TextView homeLoginName_tv, homeLoginMail_tv;
    Button logout_btn, map_btn, personalInfo_btn;

    ExtendedFloatingActionButton logout_fbtn, shop_fbtn, custom_fbtn, info_fbtn;
    FloatingActionButton map_fbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        homeLoginMail_tv=findViewById(R.id.tv_homeLoginMail);
        homeLoginName_tv=findViewById(R.id.tv_homeLoginName);
        logout_btn=findViewById(R.id.btn_logout);
        map_btn=findViewById(R.id.btn_map);
        //fingerprint
        personalInfo_btn=findViewById(R.id.btn_verify);
        personalInfo_btn.setOnClickListener(new View.OnClickListener() {
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
        map_btn.setOnClickListener(new View.OnClickListener() {
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
        }
//        logout
        logout_btn.setOnClickListener(new View.OnClickListener() {
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

        custom_fbtn=findViewById(R.id.fbtn_custom);
        info_fbtn=findViewById(R.id.fbtn_fingerprint);
        map_fbtn=findViewById(R.id.fbtn_map);
        shop_fbtn=findViewById(R.id.fbtn_shop);
        logout_btn=findViewById(R.id.fbtn_logout);
        logout_fbtn.shir

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
                Intent intent = new Intent(HomePage.this, MapsActivity.class);
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
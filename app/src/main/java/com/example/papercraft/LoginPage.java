package com.example.papercraft;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

import static android.content.ContentValues.TAG;

public class LoginPage extends AppCompatActivity implements SignUpFragment.BottomSheetListener {


    private static final int RC_SIGN_IN = 123;
    private GoogleSignInClient mGoogleSignInClient;

    SignInButton login_btn;
    Button  signup_btn;
    private FirebaseAuth mAuth;

    TextInputLayout loginUsername_edt, loginPassword_edt;
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser!=null){
            Intent intent = new Intent(LoginPage.this, HomePage.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Window window = this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, android.R.color.transparent));
        window.getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        mAuth = FirebaseAuth.getInstance();
        createRequest();

        loginPassword_edt=findViewById(R.id.LoginPassword);
        loginUsername_edt=findViewById(R.id.LoginUsername);
        signup_btn=findViewById(R.id.btn_signup);
        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignUpFragment signUpFragment = new SignUpFragment();
                signUpFragment.show(getSupportFragmentManager(), signUpFragment.getTag());

            }
        });
        login_btn = findViewById(R.id.btn_login);
        login_btn.setSize(SignInButton.SIZE_STANDARD);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        GifImageView imageView1=findViewById(R.id.login_background);
        try {
            GifDrawable gifDrawable = new GifDrawable(getResources(), R.drawable.burning2);
            imageView1.setImageDrawable(gifDrawable);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {

        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(LoginPage.this, HomePage.class);
                            startActivity(intent);

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginPage.this, "sorry cant", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
    private void createRequest() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onButtonClicked(String text) {

        Toast.makeText(LoginPage.this,text,Toast.LENGTH_SHORT).show();

    }
    private Boolean validateName(){
        String val = loginUsername_edt.getEditText().getText().toString();

        if(val.isEmpty()) {
            loginUsername_edt.setError("Field cannot be empty");
            return false;
        }else if(val.length()>=15){
            loginUsername_edt.setError("username too long");
            return false;

        }

        else {
            loginUsername_edt.setError(null);
            loginUsername_edt.setErrorEnabled(false);
            return true;
        }

    }
    private Boolean validatePw(){
        String val = loginPassword_edt.getEditText().getText().toString();

        if(val.isEmpty()){
            loginPassword_edt.setError("Field cannot be empty");
            return false;
        }

        else{
            loginPassword_edt.setError(null);
            return true;
        }

    }
    public void loginuser(View view){
        if (!validateName() | !validatePw()){
            return;
        }
        else{
            isUser();

        }
    }

    private void isUser() {

         String userEnteredUsername = loginUsername_edt.getEditText().getText().toString().trim();
         String userEnteredPassword = loginPassword_edt.getEditText().getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");

        Query checkUser = reference.orderByChild("username").equalTo(userEnteredUsername);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    loginUsername_edt.setError(null);
                    loginPassword_edt.setErrorEnabled(false);
                    String passwordFromDB = dataSnapshot.child(userEnteredUsername).child("password").getValue(String.class);

                    Log.d(TAG, "on9"+passwordFromDB);
                    Log.d(TAG, "on92"+dataSnapshot);
                    Log.d(TAG, "on93"+dataSnapshot.child(userEnteredPassword));
                    Log.d(TAG, "on94"+dataSnapshot.child(userEnteredPassword).child("password"));
                    Log.d(TAG, "on95"+dataSnapshot.child(userEnteredPassword).child("password").getValue(String.class));

                    if(passwordFromDB.equals(userEnteredPassword)){
                        Intent intent = new Intent(getApplicationContext(), HomePage.class);
                        startActivity(intent);
                    }else {
                        loginPassword_edt.setError("Wrong Password");
                        loginPassword_edt.requestFocus();
                    }
                }
                else {
                    loginUsername_edt.setError("No such User exist");
                    loginPassword_edt.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}
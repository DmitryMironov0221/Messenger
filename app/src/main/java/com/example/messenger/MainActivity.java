package com.example.messenger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

//    private static final String LOG_TAG = "MainActivityPizdeeeeeth";
//    private FirebaseAuth auth;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonLogin;
    private TextView textViewForgotPassword;
    private TextView textViewViewRegister;

    private LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        init();
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        observeViewModel();
        setupClickListener();


//        auth = FirebaseAuth.getInstance();
//        auth.signOut();
//        auth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser user = auth.getCurrentUser();
//                if(user == null){
//                    Log.d(LOG_TAG, "Not authorized");
//                }else {
//                    Log.d(LOG_TAG, "Authorized " + user.getUid());
//                }
//            }
//        });
//        auth.signInWithEmailAndPassword("dimamironov930@gmail.com","111111").addOnSuccessListener(new OnSuccessListener<AuthResult>() {
//            @Override
//            public void onSuccess(AuthResult authResult) {
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Log.d(LOG_TAG,e.getMessage());
//                Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
//            }
//        });
//        auth.sendPasswordResetEmail("dimamironov930@gmail.com").addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void unused) {
//                Log.d(LOG_TAG,"success");
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Log.d(LOG_TAG,e.getMessage());
//            }
//        });
//        auth.createUserWithEmailAndPassword("dimamironov930@gmail.com","111111").addOnSuccessListener(new OnSuccessListener<AuthResult>() {
//            @Override
//            public void onSuccess(AuthResult authResult) {
//                FirebaseUser user = auth.getCurrentUser();
//                if(user == null){
//                    Log.d(LOG_TAG, "Not authorized");
//                }else {
//                    Log.d(LOG_TAG, "Authorized");
//                }
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Log.d(LOG_TAG,e.getMessage());
//                Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
//            }
//        });
    }
private void setupClickListener(){
    buttonLogin.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();
            viewModel.login(email,password);
        }
    });
    textViewForgotPassword.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = ForgotPasswordActivity.newIntent(
                    MainActivity.this,
                    editTextEmail.getText().toString().trim());
            startActivity(intent);
        }
    });
    textViewViewRegister.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = RegistrationActivity.newIntent(MainActivity.this);
            startActivity(intent);
        }
    });
}
    private void observeViewModel(){
        viewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errorMessage) {
                if (errorMessage !=null){
                    Toast.makeText(MainActivity.this,errorMessage,Toast.LENGTH_SHORT).show();
                }
            }
        });
        viewModel.getUser().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null){
                    Intent intent = UsersActivity.newIntent(MainActivity.this,firebaseUser.getUid());
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
    private void init(){
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        buttonLogin = findViewById(R.id.buttonLogin);
        textViewForgotPassword = findViewById(R.id.textView);
        textViewViewRegister = findViewById(R.id.textViewRegister);
    }
    public static Intent newIntent(Context context){
        return new Intent(context, MainActivity.class);
    }
}
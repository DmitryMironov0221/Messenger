package com.example.messenger;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationViewModel extends ViewModel {
    private FirebaseAuth auth;

    private MutableLiveData<String> error = new MutableLiveData<>();
    private MutableLiveData<FirebaseUser> user = new MutableLiveData<>();

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference usersReference;

    public RegistrationViewModel() {
        auth =FirebaseAuth.getInstance();
        auth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null){
                    user.setValue(firebaseAuth.getCurrentUser());
                }
            }
        });
        firebaseDatabase = FirebaseDatabase.getInstance();
        usersReference = firebaseDatabase.getReference("Users");

    }
    public LiveData<String> getError(){
        return error;
    }
    public LiveData<FirebaseUser> getUser(){
        return user;
    }

    public void signUp(
            String email,
            String password,
            String name,
            String lastName
    ){
            auth.createUserWithEmailAndPassword(email,password)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            FirebaseUser firebaseUser = authResult.getUser();
                            if (firebaseUser == null){
                                return;
                            }
                            User user = new User(
                                    firebaseUser.getUid(),
                                    name,
                                    lastName,
                                    false
                            );
                            usersReference.child(user.getId()).setValue(user);// child используется
                            // для того, чтобы можно было передавать свой класс
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                error.setValue(e.getMessage());
            }
        });
    }
}

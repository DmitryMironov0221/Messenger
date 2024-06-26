package com.example.messenger;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordViewModel extends ViewModel {
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private MutableLiveData<String> error = new MutableLiveData<>();
    private MutableLiveData<Boolean> success = new MutableLiveData<>();

    public MutableLiveData<String> getError() {
        return error;
    }

    public MutableLiveData<Boolean> isSuccess() {
        return success;
    }
    public void resetPassword(String email){
        auth.sendPasswordResetEmail(email).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                error.setValue(e.getMessage());
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                success.setValue(true);
            }
        });
    }
}

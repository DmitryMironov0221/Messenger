package com.example.messenger;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ChatViewModelFactory implements ViewModelProvider.Factory { // создан для того чтобы
    // создавать ViewModel Provider и передавать необходимые параметры такие как currentUserId и otherUserId в ChatViewModel
    //ChatViewModelFactory это некая фабрика ViewModel
    private String currentUserId;
    private String otherUserId;

    public ChatViewModelFactory(String currentUserId, String otherUserId) {
        this.currentUserId = currentUserId;
        this.otherUserId = otherUserId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ChatViewModel(currentUserId,otherUserId) ;
    }
}

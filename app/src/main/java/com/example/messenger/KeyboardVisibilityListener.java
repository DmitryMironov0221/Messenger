package com.example.messenger;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;

public class KeyboardVisibilityListener implements ViewTreeObserver.OnGlobalLayoutListener {

    private final View rootView;
    private final OnKeyboardVisibilityListener listener;
    private final Rect r = new Rect();

    public KeyboardVisibilityListener(View rootView, OnKeyboardVisibilityListener listener) {
        this.rootView = rootView;
        this.listener = listener;
    }

    @Override
    public void onGlobalLayout() {
        rootView.getWindowVisibleDisplayFrame(r);
        int screenHeight = rootView.getRootView().getHeight();

        int keypadHeight = screenHeight - r.bottom;

        boolean isVisible = keypadHeight > screenHeight * 0.15;

        listener.onVisibilityChange(isVisible);
    }

    public interface OnKeyboardVisibilityListener {
        void onVisibilityChange(boolean isVisible);
    }
}

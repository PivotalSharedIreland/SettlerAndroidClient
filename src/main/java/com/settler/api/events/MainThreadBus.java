package com.settler.api.events;

import android.os.Handler;
import android.os.Looper;

import com.squareup.otto.Bus;

/**
 * This class overrides the default Bus implementation in a way that is
 * assures that a post will run only in the Android Main Thread (UI Thread)
 */
public class MainThreadBus extends Bus {

    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public void post(final Object event) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            super.post(event);
        } else {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    MainThreadBus.super.post(event);
                }
            });
        }
    }

}

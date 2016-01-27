package com.settler;

import android.app.Application;

import roboguice.RoboGuice;

public class SettlerApplication extends Application {

    private static SettlerApplication applicationInstance;

    public static SettlerApplication getApplication() {
        return applicationInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        applicationInstance = this;
        RoboGuice.overrideApplicationInjector(
                this,
                RoboGuice.newDefaultRoboModule(this),
                new SettlerApplicationModule());
    }
}
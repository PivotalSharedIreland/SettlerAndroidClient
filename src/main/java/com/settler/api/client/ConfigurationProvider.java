package com.settler.api.client;

import android.util.Log;

import com.settler.BuildConfig;

public class ConfigurationProvider implements HttpConfigurationProvider {
    public ConfigurationProvider() {
        Log.d("ConfigurationProvider","I ran!!!");

    }

    @Override
    public String getBaseURL() {
        return BuildConfig.base_url;
    }
}

package com.settler.api.client;

import android.content.Intent;

import com.google.inject.Inject;
import com.settler.api.events.PropertiesAvailableEvent;
import com.squareup.otto.Bus;

import java.io.IOException;

import roboguice.service.RoboIntentService;

public class SettlerAPIService extends RoboIntentService {

    public SettlerAPIService() {
        super("SettlerAPIService");
    }

    @Inject
    private Bus eventBus;

    @Inject
    private APIClient apiClient;

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            eventBus.post(new PropertiesAvailableEvent(apiClient.listProperties()));
        } catch (IOException e) {
            //TODO Handle exception
            e.printStackTrace();
        }
    }
}

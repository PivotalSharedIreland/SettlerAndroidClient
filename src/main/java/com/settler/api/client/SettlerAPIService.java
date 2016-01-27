package com.settler.api.client;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.google.inject.Inject;
import com.settler.Constants;
import com.settler.api.Property;

import java.io.IOException;
import java.util.List;

import roboguice.service.RoboIntentService;

import static com.settler.Constants.ExtrasKeys.PROPERTIES_LIST;
import static com.settler.Constants.IntentFilterKeys.PROPERTIES_LIST_RESULT;

public class SettlerAPIService extends RoboIntentService {

    public SettlerAPIService() {
        super("SettlerAPIService");
    }

    @Inject
    private APIClient apiClient;

    @Override
    protected void onHandleIntent(Intent intent) {

        try {
            final List<Property> properties = apiClient.listProperties();

            final Intent resultIntent = new Intent(PROPERTIES_LIST_RESULT);
            resultIntent.setPackage(getApplicationInfo().packageName);
            resultIntent.putExtra(PROPERTIES_LIST, properties.toArray(new Property[properties.size()]));

            LocalBroadcastManager manager = LocalBroadcastManager.getInstance(this);
            manager.sendBroadcast(resultIntent);

        } catch (IOException e) {
            //TODO Handle exception
            e.printStackTrace();
        }
    }
}

package com.settler.api;

import android.content.Context;
import android.content.Intent;

import com.settler.PropertyListReceiver;

import roboguice.receiver.RoboBroadcastReceiver;

import static com.settler.Constants.ExtrasKeys.PROPERTIES_LIST;
import static com.settler.Constants.IntentFilterKeys.PROPERTIES_LIST_RESULT;

public class ApiBroadcastReceiver extends RoboBroadcastReceiver {

    private PropertyListReceiver propertyListReceiver;

    public ApiBroadcastReceiver() {

    }

    public ApiBroadcastReceiver(PropertyListReceiver propertyListReceiver) {
        this.propertyListReceiver = propertyListReceiver;
    }

    @Override
    protected void handleReceive(Context context, Intent intent) {
        super.handleReceive(context, intent);

        if(PROPERTIES_LIST_RESULT.equals(intent.getAction())){
            propertyListReceiver.updatePropertyList((Property[]) intent.getParcelableArrayExtra(PROPERTIES_LIST));
        }
    }
}

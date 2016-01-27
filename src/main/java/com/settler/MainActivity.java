package com.settler;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.TextView;

import com.settler.api.ApiBroadcastReceiver;
import com.settler.api.Property;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

import static com.settler.Constants.ExtrasKeys.PROPERTIES_LIST;
import static com.settler.Constants.IntentFilterKeys.PROPERTIES_LIST_RESULT;
import static com.settler.Constants.ResultCodes.PROPERTIES_LIST_OBTAINED;

@ContentView(R.layout.activity_main)
public class MainActivity extends RoboActivity implements PropertyListReceiver {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private BroadcastReceiver propertyListReceiver;

    @InjectView(R.id.textView)
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //register receiver
        registerBroadcastReceivers();
        obtainPropertyList();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //unregister receiver
        unregisterReceivers();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        //TODO implement it!!!!!!!
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == PROPERTIES_LIST_OBTAINED) {
            textView.setText("Obtained " + data.getParcelableArrayExtra(PROPERTIES_LIST).length + " results");
            Log.d(LOG_TAG, "Obtained " + data.getParcelableArrayExtra(PROPERTIES_LIST) + " results");
        }
    }

    private void obtainPropertyList() {
        Intent intent = new Intent("settler.intent.action.LIST_PROPERTIES");
        intent.setPackage(getPackageName());
        startService(intent);
    }

    private void registerBroadcastReceivers() {
        final IntentFilter filter = new IntentFilter(PROPERTIES_LIST_RESULT);
        propertyListReceiver = new ApiBroadcastReceiver(this);
        propertyListReceiver.setOrderedHint(true);

        LocalBroadcastManager.getInstance(this).registerReceiver(propertyListReceiver, filter);
    }

    private void unregisterReceivers() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(propertyListReceiver);
    }

    @Override
    public void updatePropertyList(Property[] properties) {

        textView.setText("Obtained " + properties.length + " results");
        Log.d(LOG_TAG, "Obtained " + properties + " results");

    }
}

package com.settler;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.inject.Inject;
import com.settler.api.Property;
import com.settler.api.events.PropertiesAvailableEvent;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.Arrays;
import java.util.List;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

import static com.settler.Constants.ExtrasKeys.PROPERTIES_LIST;
import static com.settler.Constants.IntentFilterKeys.PROPERTIES_LIST_REQUEST;
import static com.settler.Constants.ResultCodes.PROPERTIES_LIST_OBTAINED;

@ContentView(R.layout.activity_main)
public class MainActivity extends RoboActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Inject
    private Bus eventBus;

    @InjectView(R.id.listView)
    ListView listView;

    private List<Property> properties;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            final Property[] propertiesArray = (Property[]) savedInstanceState.getParcelableArray(PROPERTIES_LIST);
            if (propertiesArray != null && propertiesArray.length > 0) {
                properties = Arrays.asList(propertiesArray);
                listView.setAdapter(new PropertyListAdapter(this, properties));
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        eventBus.register(this);
        if (properties == null) {
            obtainPropertyList();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        eventBus.unregister(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (properties != null) {
            outState.putParcelableArray(PROPERTIES_LIST, properties.toArray(new Property[properties.size()]));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == PROPERTIES_LIST_OBTAINED) {
            Log.d(LOG_TAG, "Obtained " + data.getParcelableArrayExtra(PROPERTIES_LIST) + " results");
        }
    }

    private void obtainPropertyList() {
        Intent intent = new Intent(PROPERTIES_LIST_REQUEST);
        intent.setPackage(getPackageName());
        startService(intent);
    }

    @Subscribe
    public void propertiesAvailable(final PropertiesAvailableEvent event) {
        String text = "Obtained " + event.getProperties().size() + " results";
        Log.d(LOG_TAG, text);
        properties = event.getProperties();
        listView.setAdapter(new PropertyListAdapter(this, properties));
    }
}

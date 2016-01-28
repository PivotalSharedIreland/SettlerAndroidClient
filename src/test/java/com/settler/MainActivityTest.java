package com.settler;

import android.content.Intent;
import android.widget.TextView;

import com.settler.api.ApiBaseTest;
import com.settler.api.Property;
import com.settler.api.client.APIClient;

import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import static com.jayway.awaitility.Awaitility.await;
import static com.settler.Constants.IntentFilterKeys.PROPERTIES_LIST_REQUEST;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNull;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class MainActivityTest extends ApiBaseTest {

    private APIClient serviceMock = Mockito.mock(APIClient.class);

    @Test
    //TODO after handling state, test it having the list and not having the list on savedState
    public void startServiceOnStart() throws Exception {
        ActivityController<MainActivity> activityController = Robolectric.buildActivity(MainActivity.class).create();

        MainActivity mainActivity = activityController.get();
        activityController.start();

        assertNull(Shadows.shadowOf(mainActivity).getNextStartedService());

        activityController.resume();

        Intent expectedIntent = new Intent(PROPERTIES_LIST_REQUEST);
        expectedIntent.setPackage(mainActivity.getPackageName());
        assertEquals(Shadows.shadowOf(mainActivity).getNextStartedService(), expectedIntent);
    }

    @Test
    public void handleNewPropertyList() throws Exception {

        ActivityController<MainActivity> activityController = Robolectric.buildActivity(MainActivity.class).create();
        MainActivity mainActivity = activityController.get();

        activityController.start();

        assertEquals(((TextView) mainActivity.findViewById(R.id.textView)).getText(), "Hello Espresso!");

        activityController.resume();

        //fake broadcastReceiver response
        mainActivity.updatePropertyList(new Property[]{new Property(), new Property()});
        await().atMost(5, TimeUnit.SECONDS).until(propertiesReceived(mainActivity), CoreMatchers.equalTo("Obtained 2 results"));
    }

    private Callable<String> propertiesReceived(final MainActivity mainActivity) {
        return new Callable<String>() {
            public String call() throws Exception {
                return ((TextView) mainActivity.findViewById(R.id.textView)).getText().toString();
            }
        };
    }
}

package com.settler;

import android.content.Intent;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;

import static com.settler.Constants.IntentFilterKeys.PROPERTIES_LIST_REQUEST;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class MainActivityTest {

    @Test
    public void shouldThrowGetPropertiesIntent() throws Exception {
        MainActivity mainActivity = Robolectric.setupActivity(MainActivity.class);
        assertTrue(mainActivity != null);

        Intent expectedIntent = new Intent(PROPERTIES_LIST_REQUEST);
        expectedIntent.setPackage(mainActivity.getPackageName());
        assertEquals(Shadows.shadowOf(mainActivity).getNextStartedService(), expectedIntent);
    }

}

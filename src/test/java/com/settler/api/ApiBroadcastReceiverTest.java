package com.settler.api;

import android.content.Intent;

import com.settler.BuildConfig;
import com.settler.Constants;
import com.settler.PropertyListReceiver;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static com.settler.Constants.ExtrasKeys.PROPERTIES_LIST;
import static com.settler.Constants.IntentFilterKeys.PROPERTIES_LIST_RESULT;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ApiBroadcastReceiverTest {

    @Test
    public void shouldSendPropertiesListToReceiver() throws Exception {
        Intent intent = mock(Intent.class);
        PropertyListReceiver showPropertyListActivity = Mockito.mock(PropertyListReceiver.class);
        ApiBroadcastReceiver apiBroadcastReceiver = new ApiBroadcastReceiver(showPropertyListActivity);

        Property[] properties = new Property[]{new Property()};
        when(intent.getAction()).thenReturn(Constants.IntentFilterKeys.PROPERTIES_LIST_RESULT);
        when(intent.getParcelableArrayExtra(Constants.ExtrasKeys.PROPERTIES_LIST)).thenReturn(properties);

        apiBroadcastReceiver.handleReceive(null, intent);

        verify(showPropertyListActivity, times(1)).updatePropertyList(properties);
    }

    @Test
    public void shouldDoNothingIfActionIsNotTheExpectedOne() throws Exception {

        Intent intent = mock(Intent.class);

        PropertyListReceiver showPropertyListActivity = Mockito.mock(PropertyListReceiver.class);
        ApiBroadcastReceiver apiBroadcastReceiver = new ApiBroadcastReceiver(showPropertyListActivity);

        Property[] properties = new Property[]{new Property()};
        when(intent.getAction()).thenReturn("unknownAction");
        when(intent.getParcelableArrayExtra(Constants.ExtrasKeys.PROPERTIES_LIST)).thenReturn(properties);

        apiBroadcastReceiver.handleReceive(null, intent);

        verify(showPropertyListActivity, times(0)).updatePropertyList(any(Property[].class));
    }
}
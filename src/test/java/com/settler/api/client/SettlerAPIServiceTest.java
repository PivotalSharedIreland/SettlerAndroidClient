package com.settler.api.client;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Parcelable;
import android.support.v4.content.LocalBroadcastManager;

import com.google.inject.AbstractModule;
import com.settler.BuildConfig;
import com.settler.Constants;
import com.settler.PropertyListReceiver;
import com.settler.SettlerApplication;
import com.settler.api.ApiBaseTest;
import com.settler.api.Property;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import roboguice.RoboGuice;

import static com.settler.Constants.IntentFilterKeys.PROPERTIES_LIST_REQUEST;
import static com.settler.Constants.IntentFilterKeys.PROPERTIES_LIST_RESULT;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.robolectric.Robolectric.buildService;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class SettlerAPIServiceTest extends ApiBaseTest {

    private APIClient serviceMock = Mockito.mock(APIClient.class);
    private PropertyListReceiver propertyListReceiver = Mockito.mock(PropertyListReceiver.class);

    @Before
    public void setup() {
        // Override the default RoboGuice module
        RoboGuice.overrideApplicationInjector(SettlerApplication.getApplication(), new MyTestModule());
    }

    @After
    public void teardown() {
        // Don't forget to tear down our custom injector to avoid polluting other test classes
        RoboGuice.Util.reset();
    }

    @Test
    public void onHandleEventReturnsListOfProperties() throws Exception {

        SettlerAPIService service = buildService(SettlerAPIService.class).create().get();

        when(serviceMock.listProperties()).thenReturn(getProperties());

        Intent intent = new Intent(PROPERTIES_LIST_REQUEST);
        intent.setPackage(SettlerApplication.getApplication().getPackageName());

        LocalBroadcastManager instance = LocalBroadcastManager.getInstance(SettlerApplication.getApplication());
        ArgumentCaptor<Intent> argumentCaptor = ArgumentCaptor.forClass(Intent.class);
        BroadcastReceiver receiver = mock(BroadcastReceiver.class);
        instance.registerReceiver(receiver, new IntentFilter(PROPERTIES_LIST_RESULT));

        service.onHandleIntent(intent);

        verify(serviceMock, times(1)).listProperties();
        verify(receiver, times(1)).onReceive(any(Context.class), argumentCaptor.capture());

        Intent receivedIntent = argumentCaptor.getValue();
        Parcelable[] parcelables = receivedIntent.getParcelableArrayExtra(Constants.ExtrasKeys.PROPERTIES_LIST);
        assertTrue(parcelables.length == 2);
        assertTrue(((Property) parcelables[0]).getId() == 1L);
    }

    public class MyTestModule extends AbstractModule {
        @Override
        protected void configure() {
            bind(APIClient.class).toInstance(serviceMock);
        }
    }
}
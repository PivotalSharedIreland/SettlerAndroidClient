package com.settler.api.client;

import android.content.Intent;

import com.google.inject.AbstractModule;
import com.settler.BuildConfig;
import com.settler.SettlerApplication;
import com.settler.api.ApiBaseTest;
import com.settler.api.events.MainThreadBus;
import com.settler.api.events.PropertiesAvailableEvent;
import com.squareup.otto.Bus;

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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.robolectric.Robolectric.buildService;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class SettlerAPIServiceTest extends ApiBaseTest {

    private APIClient apiClientMock;

    private Bus eventBus;

    @Before
    public void setup() {
        // Override the default RoboGuice module

        apiClientMock = Mockito.mock(APIClient.class);
        eventBus = Mockito.mock(MainThreadBus.class);
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

        when(apiClientMock.listProperties()).thenReturn(buildPropertiesList(2));

        Intent intent = new Intent(PROPERTIES_LIST_REQUEST);
        intent.setPackage(SettlerApplication.getApplication().getPackageName());

        ArgumentCaptor<PropertiesAvailableEvent> argumentCaptor = ArgumentCaptor.forClass(PropertiesAvailableEvent.class);

        service.onHandleIntent(intent);

        verify(apiClientMock, times(1)).listProperties();
        verify(eventBus, times(1)).post(argumentCaptor.capture());

        PropertiesAvailableEvent receivedIntent = argumentCaptor.getValue();
        assertTrue(receivedIntent.getProperties().size() == 2);
        assertEquals(receivedIntent.getProperties().get(0).getId().longValue(), 1L);
    }

    public class MyTestModule extends AbstractModule {
        @Override
        protected void configure() {
            bind(APIClient.class).toInstance(apiClientMock);
            bind(Bus.class).toInstance(eventBus);
        }
    }

}
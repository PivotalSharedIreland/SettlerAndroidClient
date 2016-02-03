package com.example.activity;

import android.app.Activity;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.util.Log;

import com.google.inject.AbstractModule;
import com.google.inject.Binder;
import com.settler.MainActivity;
import com.settler.R;
import com.settler.SettlerApplication;
import com.settler.SettlerApplicationModule;
import com.settler.api.client.APIClient;
import com.settler.api.client.DefaultAPIClient;
import com.settler.api.client.HttpConfigurationProvider;
import com.settler.api.events.MainThreadBus;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import com.squareup.otto.Bus;

import org.hamcrest.CoreMatchers;
import org.hamcrest.core.AllOf;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import roboguice.RoboGuice;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class SettlerUiTest {



    private MockWebServer server;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new RoboGuiceActivityTestRule<>(MainActivity.class);

    private class RoboGuiceActivityTestRule<T extends Activity> extends ActivityTestRule<T> {
        private static final int PORT = 8081;

        private TestConfigurationProvider provider;

        public RoboGuiceActivityTestRule(Class<T> activityClass) {
            super(activityClass);
        }

        @Override
        protected void beforeActivityLaunched() {
            server = new MockWebServer();
            try {
                server.play(PORT);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            server.enqueue(new MockResponse().setResponseCode(200).setHeader("Content-Type", "application/json").setBody("[{\"id\":\"1\",\"address\":\"20 Evenmorec111\"}]"));

            provider = new TestConfigurationProvider(server.getUrl("/").toString());
            RoboGuice.overrideApplicationInjector(SettlerApplication.getApplication(), new MyTestModule());
        }

        public class MyTestModule extends AbstractModule {
            @Override
            public void configure() {
                bind(HttpConfigurationProvider.class).toInstance(provider);
                bind(APIClient.class).to(DefaultAPIClient.class);
                bind(Bus.class).to(MainThreadBus.class).asEagerSingleton();
            }
        }

        @Override
        protected void afterActivityFinished() {
            try {
                server.shutdown();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            RoboGuice.Util.reset();
        }
    }

    @Test
    public void showPropertiesList() {

        assertThat(server.getRequestCount(), CoreMatchers.equalTo(1));
        onView(AllOf.allOf(withId(R.id.property_address), withText("20 Evenmorec111"))).check(matches(isDisplayed()));
    }

    class TestConfigurationProvider implements HttpConfigurationProvider {

        public String url;

        public TestConfigurationProvider(String url) {
            this.url = url;
        }
        @Override
        public String getBaseURL() {
            Log.d("INTEGRATION TEST", "Server URL: " + url);
            return url;
        }

    }

}

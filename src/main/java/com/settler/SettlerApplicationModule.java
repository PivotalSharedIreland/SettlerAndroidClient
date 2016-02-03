package com.settler;

import com.google.inject.Binder;
import com.google.inject.Key;
import com.google.inject.Module;
import com.google.inject.Provider;
import com.google.inject.Scope;
import com.settler.api.client.APIClient;
import com.settler.api.client.ConfigurationProvider;
import com.settler.api.client.DefaultAPIClient;
import com.settler.api.client.HttpConfigurationProvider;
import com.settler.api.events.MainThreadBus;
import com.squareup.otto.Bus;

import java.lang.reflect.Constructor;

public class SettlerApplicationModule implements Module {

    @Override
    public void configure(Binder binder) {
        binder.bind(HttpConfigurationProvider.class).to(ConfigurationProvider.class);
        binder.bind(APIClient.class).to(DefaultAPIClient.class);
        binder.bind(Bus.class).to(MainThreadBus.class).asEagerSingleton();
    }
}

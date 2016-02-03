package com.settler.api.client;

import com.google.inject.Inject;
import com.settler.api.Property;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

public class DefaultAPIClient implements APIClient {

    private final APIService apiService;

    @Inject
    public DefaultAPIClient(HttpConfigurationProvider configurationProvider) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(configurationProvider.getBaseURL())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(APIService.class);
    }

    @Override
    public List<Property> listProperties() throws IOException {
        Call<List<Property>> call = apiService.listProperties();
        return call.execute().body();
    }

}

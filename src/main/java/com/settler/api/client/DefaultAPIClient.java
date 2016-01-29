package com.settler.api.client;

import android.os.Binder;

import com.settler.api.Property;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

public class DefaultAPIClient extends Binder implements APIClient {

    private final Retrofit retrofit;
    private final APIService apiService;
    private final String url = "http://settler.cfapps.io";

    public DefaultAPIClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
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

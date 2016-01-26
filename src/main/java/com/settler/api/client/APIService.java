package com.settler.api.client;

import com.settler.api.Property;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIService {

    @GET("properties")
    Call<List<Property>> listProperties();

}

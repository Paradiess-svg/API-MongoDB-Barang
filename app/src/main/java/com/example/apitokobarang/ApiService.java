package com.example.apitokobarang;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {
    @GET("api/barang")
    Call<List<Barang>> getAllBarang();

    @POST("api/barang")
    Call<Barang> createBarang(@Body Barang barang);

    @PUT("api/barang/{id}")
    Call<Barang> updateBarang(@Path("id") String id, @Body Barang barang);

    @DELETE("api/barang/{id}")
    Call<Void> deleteBarang(@Path("id") String id);
}

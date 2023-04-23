package com.talo.test.data;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MainInterface {

    @GET("posts")
    Call<List<MainData>> getData(@Query("userId") int id);

}

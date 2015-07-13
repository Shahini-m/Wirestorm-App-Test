package com.appwithkotlin.mshahini.wirestormapplication.android.service;

import com.appwithkotlin.mshahini.wirestormapplication.android.model.User;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by mshahini on 13.07.2015.
 */
public interface WirestormService {
    @GET("/wirestorm/assets/response.json")
    void userList(Callback<List<User>> usersCallback);
}

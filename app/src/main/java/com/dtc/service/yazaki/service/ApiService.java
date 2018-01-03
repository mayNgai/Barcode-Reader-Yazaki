package com.dtc.service.yazaki.service;

import com.dtc.service.yazaki.model.TblItem;
import com.dtc.service.yazaki.model.TblMember;
import com.dtc.service.yazaki.model.TblPickUpAndDestination;
import com.dtc.service.yazaki.model.TblSetting;
import com.dtc.service.yazaki.model.TblTask;
import com.dtc.service.yazaki.model.TblVehicle;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by May on 11/6/2017.
 */

public class ApiService {
    public static final String FORUM_SERVER_URL = "http://203.151.232.148:8080/";
    private ForumApi mForumApi;
    public ApiService() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        OkHttpClient okHttpClient = builder.build();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(FORUM_SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();

        mForumApi = retrofit.create(ForumApi.class);
    }
    public ForumApi getApi() {
        return mForumApi;
    }

    public interface ForumApi {

        @POST("login")
        public Observable<List<TblMember>> getLogin(@Body TblMember member);

        @POST("delivery")
        public Observable<List<TblTask>> getTasks(@Body TblMember member);

        @POST("setting")
        public Observable<List<TblSetting>> getSetting(@Body TblMember member);

        @POST("up_setting")
        public Observable<TblSetting> updateSetting(@Body TblSetting setting);

        @POST("get_pick_up")
        public Observable<List<TblPickUpAndDestination>> getPickup(@Body TblMember member);

        @POST("get_destination")
        public Observable<List<TblPickUpAndDestination>> getDestination(@Body TblMember member);

        @POST("get_item")
        public Observable<List<TblItem>> getItem(@Body TblMember member);

        @POST("get_vehicle")
        public Observable<List<TblVehicle>> getVehicle(@Body TblMember member);

        @POST("get_delivery")
        public Observable<List<TblTask>> getTask(@Body TblMember member);

        @POST("add_delivery")
        public Observable<TblTask> addTask(@Body TblTask task);

        @POST("up_delivery")
        public Observable<TblTask> updateTask(@Body TblTask task);

        @POST("del_delivery")
        public Observable<TblTask> deleteTask(@Body TblTask task);

        @POST("get_bussiness_person")
        public Observable<List<TblTask>> getBussinessPerson(@Body TblTask task);
    }
}

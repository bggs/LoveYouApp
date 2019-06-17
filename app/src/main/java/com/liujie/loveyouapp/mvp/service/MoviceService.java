package com.liujie.loveyouapp.mvp.service;

import com.liujie.loveyouapp.mvp.model.BaseResponse;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface MoviceService {
    @FormUrlEncoded
    @POST("/api/LogIn/Test")
    Observable<BaseResponse> test(@Field("userid") String userId, @Field("token") String token, @Field("timestamp") String timestamp);


}

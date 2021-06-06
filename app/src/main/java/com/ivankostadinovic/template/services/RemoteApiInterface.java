package com.ivankostadinovic.template.services;

import com.ivankostadinovic.template.models.response.BaseResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RemoteApiInterface {

    @GET("api/example-get")
    Single<Response<BaseResponse>> exampleGetRequest(@Query("limit") long limit,
        @Query("offset") long offset);

    @PUT("api/example-put/{id]")
    Single<Response<BaseResponse>> examplePutRequest(@Path("id") String id);

    @DELETE("api/example-delete/{id}")
    Single<Response<BaseResponse>> exampleDeleteRequest(@Path("id") String id);

    @FormUrlEncoded
    @POST("api/example-post-with-form")
    Single<Response<BaseResponse>> examplePostFormUrlEncoded(
        @Field("field2") long field2,
        @Field("field1") String field1);

    @POST("api/example-post-with-body")
    Single<Response<BaseResponse>> examplePostWithBody(
        @Body Object requestMakePayment);

}

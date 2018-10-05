package com.example.css.base.interfaces;



import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIInterface {


    @GET("/api/unknown")
    public Call<String> doGetListResources();

    @POST("/upload/Multipartupload.php")
    Call<ResponseBody> createUser(@Body RequestBody files);

    @GET("/api/users?")
    Call<String> doGetUserList(@Query("page") String page);

    @FormUrlEncoded
    @POST("/api/users?")
    Call<String> doCreateUserWithField(@Field("name") String name, @Field("job") String job);
}

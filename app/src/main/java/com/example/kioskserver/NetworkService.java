package com.example.kioskserver;

import java.util.List;

import okhttp3.internal.Version;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface NetworkService {
    /*
    @POST("/api/versions/")
    Call<Version> port_version(@Body Version version);

    @PATCH("/api/versions/{pk}/")
    Call<Version> patch_version(@Path("pk") int pk, @Body Version version);
    @DELETE("/api/versions/{pk}/")
    Call<Version> delete_version(@Path("pk") int pk);
    @GET("/api/versions/")
    Call<List<Version>> get_version();
    @GET("/api/versions/{pk}/")
    Call<Version> get_pk_version(@Path("pk") int pk);
*/
    @POST("/mobilekiosk/shopinfo/")
    Call<ShopInfo> post_shopinfo(@Body ShopInfo shopinfo);
    @PATCH("/mobilekiosk/shopinfo/{pk}/")
    Call<ShopInfo> patch_shopinfo(@Path("pk") int pk, @Body ShopInfo shopinfo);
    @DELETE("/mobilekiosk/shopinfo/{pk}/")
    Call<ShopInfo> delete_shopinfo(@Path("pk") int pk);
    @GET("/mobilekiosk/shopinfo/")
    Call<List<ShopInfo>> get_shopinfo();
    @GET("/mobilekiosk/shopinfo/{pk}/")
    Call<ShopInfo> get_pk_shopinfo(@Path("pk") int pk);

    @POST("/mobilekiosk/order/")
    Call<OrderInfo> post_orderinfo(@Body OrderInfo orderinfo);
    @PATCH("/mobilekiosk/order/{pk}/")
    Call<OrderInfo> patch_orderinfo(@Path("pk") int pk, @Body OrderInfo orderinfo);
    @DELETE("/mobilekiosk/order/{pk}/")
    Call<OrderInfo> delete_orderinfo(@Path("pk") int pk);
    @GET("/mobilekiosk/order/")
    Call<List<OrderInfo>> get_orderinfo();
    @GET("/mobilekiosk/order/{pk}/")
    Call<List<OrderInfo>> get_pk_orderinfo(@Path("pk") int pk);

    @POST("/mobilekiosk/orderdetail/")
    Call<OrderDetail> post_orderdetail(@Body OrderDetail orderdetail);
    @PATCH("/mobilekiosk/orderdetail/{pk}/")
    Call<OrderDetail> patch_orderdetail(@Path("pk") int pk, @Body OrderDetail orderdetail);
    @DELETE("/mobilekiosk/orderdetail/{pk}/")
    Call<OrderDetail> delete_orderdetail(@Path("pk") int pk);
    @GET("/mobilekiosk/orderdetail/")
    Call<List<OrderDetail>> get_orderdetail();
    @GET("/mobilekiosk/orderdetail/{pk}/")
    Call<List<OrderDetail>> get_pk_orderdetail(@Path("pk") int pk);

    @POST("/mobilekiosk/shopmenu/")
    Call<ShopMenu> post_shopmenu(@Body ShopMenu shopmenu);
    @PATCH("/mobilekiosk/shopmenu/{pk}/")
    Call<ShopMenu> patch_shopmenu(@Path("pk") int pk, @Body ShopMenu shopmenu);
    @DELETE("/mobilekiosk/shopmenu/{pk}/")
    Call<ShopMenu> delete_shopmenu(@Path("pk") int pk);
    @GET("/mobilekiosk/shopmenu/")
    Call<List<ShopMenu>> get_shopmenu();
    @GET("/mobilekiosk/shopmenu/{pk}/")
    Call<List<ShopMenu>> get_pk_shopmenu(@Path("pk") int pk);


}

//참조 : https://duzi077.tistory.com/129?category=703147
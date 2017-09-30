package com.example.jsoup.jsoupdemo.net;

import com.example.jsoup.jsoupdemo.bean.WebPageInfo;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Administrator on 2017/9/15.
 */

public interface ApiStores {

    String mainURL = "https://www.pengfu.com";

    @GET("https://www.pengfu.com/")
    Call<ResponseBody> loadMainData();

}

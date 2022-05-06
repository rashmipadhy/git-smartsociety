package com.kfxlabs.smartsociety.api;


import android.content.Context;

import retrofit2.Retrofit;

public class Api {

    public static String BASE_URL = "http://www.kfxlabs.com";
    public static String PORT_NO = "8083";
    public static String FULL_URL = String.format("%s%s%s", BASE_URL, ":", PORT_NO);
    static Retrofit client = RetrofitClient.getClient(FULL_URL,null);
//    private static  RetrofitClient mInstance;


    public static UserRegistration getUserRegAPIService(Context context) {
        return RetrofitClient.getClient(FULL_URL, context).create(UserRegistration.class);
      //  return client.create(UserRegistration.class);
    }

    //public static synchronized RetrofitClient getInstance(){

       // if (mInstance == null){
        //    mInstance = new RetrofitClient();
       // }
       // return  mInstance;
    //}


    public static OtpVerification getOtpAPIService(Context context) {
        return RetrofitClient.getClient(FULL_URL, context).create(OtpVerification.class);
    }

    public static Info getInfoAPIService() {
        return client.create(Info.class);
    }

    public static login getLoginAPIService(Context context) {
        return RetrofitClient.getClient(FULL_URL, context).create(login.class);
    }

    public static Upassword getPwdAPIService(Context context) {
        return RetrofitClient.getClient(FULL_URL, context).create(Upassword.class);
    }

    public static FcmToken getFcmTokenAPIService(Context context) {
        return RetrofitClient.getClient(FULL_URL, context).create(FcmToken.class);
    }

    public static GetSetting getSettingGetAPIService(Context context) {
        return RetrofitClient.getClient(FULL_URL, context).create(GetSetting.class);
    }


}
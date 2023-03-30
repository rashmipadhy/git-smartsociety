package com.kfxlabs.smartsociety.api;


import android.content.Context;

import retrofit2.Retrofit;

public class Api {

    public static String BASE_URL = "http://146.148.70.212";
    public static String PORT_NO = "8095";

   //public static String BASE_URL = "http://www.kfxlabs.com";
    //public static String PORT_NO = "8083";

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

    public static GetProfile getGetProfileAPIService(Context context) {
        return RetrofitClient.getClient(FULL_URL, context).create(GetProfile.class);
    }

    public static DgInfo getDgInfoAPIService(Context context){
        return RetrofitClient.getClient(FULL_URL, context).create(DgInfo.class); }

    public static AddDg getAddDgAPIService(Context context) {
        return RetrofitClient.getClient(FULL_URL,context).create(AddDg.class);
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

    public static PostSetting getSettingPostAPIService(Context context) {
        return RetrofitClient.getClient(FULL_URL, context).create(PostSetting.class);
    }

    public static GetDglist getDglistAPIService(Context context) {
        return RetrofitClient.getClient(FULL_URL, context).create(GetDglist.class);
    }
    public static GetTanklist getTanklistAPIService(Context context) {
        return RetrofitClient.getClient(FULL_URL, context).create(GetTanklist.class);
    }
    public static GetAlerts getAlertsAPIService(Context context) {
        return RetrofitClient.getClient(FULL_URL, context).create(GetAlerts.class);
    }
    public static GetDgAlerts getDgAlertsAPIService(Context context) {
        return RetrofitClient.getClient(FULL_URL, context).create(GetDgAlerts.class);
    }
    public static GetFuelLog getFuelLogAPIService(Context context) {
        return RetrofitClient.getClient(FULL_URL,context).create(GetFuelLog.class);
    }
    public static GetOutflowrLog getOutflowLogAPIService(Context context) {
        return RetrofitClient.getClient(FULL_URL,context).create(GetOutflowrLog.class);
    }

    public static GetInflowLog getInflowLogAPIService(Context context) {
        return RetrofitClient.getClient(FULL_URL,context).create(GetInflowLog.class);
    }

    public static GetPowerLog getPowerLogAPIService(Context context) {
        return RetrofitClient.getClient(FULL_URL,context).create(GetPowerLog.class);
    }

    public static GetTankPlot getTankPlotAPIService(Context context) {
        return RetrofitClient.getClient(FULL_URL,context).create(GetTankPlot.class);
    }

    public static GetPumpPowerLog getPumpPowerLogAPIService(Context context) {
        return RetrofitClient.getClient(FULL_URL,context).create(GetPumpPowerLog.class);
    }
    public static GetMaintLog getMaintLogAPIService(Context context) {
        return RetrofitClient.getClient(FULL_URL,context).create(GetMaintLog.class);
    }
    public static GetRunLog getRunLogAPIService(Context context) {
        return RetrofitClient.getClient(FULL_URL,context).create(GetRunLog.class);
    }
    public static GetPumpRunLog getPumpRunLogAPIService(Context context) {
        return RetrofitClient.getClient(FULL_URL,context).create(GetPumpRunLog.class);
    }
    public static AddDgFuel getAddDgFuelAPIService(Context context) {
        return RetrofitClient.getClient(FULL_URL,context).create(AddDgFuel.class);
    }

    public static AddDgMaint getAddDgMaintAPIService(Context context) {
        return RetrofitClient.getClient(FULL_URL,context).create(AddDgMaint.class);
    }

    public static AddTank getAddTankAPIService(Context context) {
        return RetrofitClient.getClient(FULL_URL,context).create(AddTank.class);
    }
    public static AddPump getAddPumpAPIService(Context context) {
        return RetrofitClient.getClient(FULL_URL,context).create(AddPump.class);
    }
    public static GetPumplist getPumplistAPIService(Context context) {
        return RetrofitClient.getClient(FULL_URL, context).create(GetPumplist.class);
    }

    public static SwitchPump getSwitchPumpAPIService(Context context) {
        return RetrofitClient.getClient(FULL_URL, context).create(SwitchPump.class);
    }
    public static GetPumpSettings getPumpSettingsGetAPIService(Context context) {
        return RetrofitClient.getClient(FULL_URL, context).create(GetPumpSettings.class);
    }
    public static PostPumpSettings getPumpSettingsPostAPIService(Context context) {
        return RetrofitClient.getClient(FULL_URL, context).create(PostPumpSettings.class);
    }

}
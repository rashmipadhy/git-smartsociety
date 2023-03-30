package com.kfxlabs.smartsociety.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kfxlabs.smartsociety.model.DgMetaDetails;
import com.kfxlabs.smartsociety.model.DgValueDetails;
import com.kfxlabs.smartsociety.model.PumpMetaDetails;
import com.kfxlabs.smartsociety.model.PumpValueDetails;
import com.kfxlabs.smartsociety.model.TankMetaDetails;
import com.kfxlabs.smartsociety.model.TankPlotDetails;
import com.kfxlabs.smartsociety.model.TankValueDetails;
import com.kfxlabs.smartsociety.module.Dglist;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SharedPrefManager {
    private static final String SHARED_PREF_NAME = "my_shared_pref";
    private SharedPreferences sharedPreferences;
   // Context context;
    private  SharedPreferences.Editor editor;
    private static  SharedPrefManager mInstance;
    private Context mCtx;



    public SharedPrefManager(Context mCtx){

        this.mCtx = mCtx;
    }

    public static synchronized SharedPrefManager getInstance(Context mCtx){
        if(mInstance == null){
            mInstance =new SharedPrefManager(mCtx);
        }
    return mInstance;
    }
    public void storeUserInfo(String userName, String phoneNumber, String emailId,String password,String orgId){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user_name", userName);
        editor.putString("phone_number", phoneNumber);
        editor.putString("email_id", emailId);
        editor.putString("org_id", orgId);
        editor.putString("password", password);
        editor.apply();
    }

    public String getUserInfo() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString("user_name", "No User Name");
    }
    public String getphoneNumb() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString("phone_number", "No User Name");
    }
    public String getEmail() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString("email_id", "");
    }
    public String getPwd() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString("password", "");
    }

    public void storeUserPwd(String password){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
      editor.putString("password", password);

        editor.apply();
    }

    public void storeUserPhone(String phoneNumber){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("uid", phoneNumber);
        editor.apply();
    }

    public String getphoneNumber() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString("uid", "");
    }

    public String getpassword() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString("password", "");
    }


    public void storeLoginInfo(String loggedIn) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("loggedInDetail",loggedIn);

        editor.apply();
    }

    public String getloggedInDetail() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString("loggedInDetail", "False");
    }


    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getInt("id",-1)!= -1;

    }
    public void storeDgInfo(final String androidID,final String app, String uphone,String location,String devpin,String devid, String dgname,String dgmake,String dgmodel,String dgkva,String dgInitialEngReading, final String dgDetailsFilesName){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("android_id", androidID);
        editor.putString("app", app);
        editor.putString("phone", uphone);
        editor.putString("location", location);
        editor.putString("devid", devid);
        editor.putString("devpin", devpin);
        editor.putString("dhmake", dgmake);
        editor.putString("model", dgmodel);
        editor.putString("name", dgname);
        editor.putString("kva", dgkva);
        editor.putString("dg", dgInitialEngReading);
        editor.putString("details", dgDetailsFilesName);

        editor.apply();
    }

    public void storeTankInfo(final String androidID,String location,String devpin,String devid, String tname,String ttype,String tcapacity,String tdepth ,String toffset,final String tankDetailsFilesName){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("android_id", androidID);
        editor.putString("location", location);
        editor.putString("devid", devid);
        editor.putString("devpin", devpin);
        editor.putString("tname", tname);
        editor.putString("ttype", ttype);
        editor.putString("tcapacity", tcapacity);
        editor.putString("tdepth", tdepth);
        editor.putString("toffset", toffset);
        editor.putString("details", tankDetailsFilesName);

        editor.apply();
    }

    public void storePumpInfo(final String androidID,String location,String devpin,String devid, String pname,String pmake,String pmodel, final String pumpDetailsFilesName){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("android_id", androidID);
        editor.putString("location", location);
        editor.putString("devid", devid);
        editor.putString("devpin", devpin);
        editor.putString("pmake", pmake);
        editor.putString("pmodel", pmodel);
        editor.putString("pname", pname);
        editor.putString("details", pumpDetailsFilesName);

        editor.apply();
    }
    public List<PumpMetaDetails> getPumpMetaDetails() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        String json = sharedPreferences.getString("pump_details", "");

        Gson gson = new Gson();
        Type dgType = new TypeToken<List<PumpMetaDetails>>(){}.getType();
        try {
            List<PumpMetaDetails> details = gson.fromJson(json, dgType);
            if(details == null) {
                return new ArrayList<>();
            }
            return details;
        } catch (Exception ex) {
            return new ArrayList<>();
        }
    }


    public void addPumpMeta(PumpMetaDetails details) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        List<PumpMetaDetails> existingPumpMetaDetailsList = getPumpMetaDetails();

        existingPumpMetaDetailsList.add(details);
        Gson gson = new Gson();
        String stringPumpList = gson.toJson(existingPumpMetaDetailsList);

        editor.putString("pump_details", stringPumpList);
        editor.apply();
    }
    public void addPumpValue(PumpValueDetails details) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        List<PumpValueDetails> existingPumpValueDetailsList = getPumpValueDetails();

        existingPumpValueDetailsList.add(details);
        Gson gson = new Gson();
        String stringDgList = gson.toJson(existingPumpValueDetailsList);

        editor.putString("pump_value_details", stringDgList);
        editor.apply();
    }

    public List<PumpValueDetails> getPumpValueDetails() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        String json = sharedPreferences.getString("pump_value_details", "");

        Gson gson = new Gson();
        Type pumpType = new TypeToken<List<PumpValueDetails>>(){}.getType();
        try {
            List<PumpValueDetails> details = gson.fromJson(json, pumpType);
            if(details == null) {
                return new ArrayList<>();
            }
            return details;
        } catch (Exception ex) {
            return new ArrayList<>();
        }
    }
    public void clearExistingPumpValues() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.remove("pump_value_details");
        editor.apply();
    }

    public List<TankMetaDetails> getTankMetaDetails() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        String json = sharedPreferences.getString("tank_details", "");

        Gson gson = new Gson();
        Type tankType = new TypeToken<List<TankMetaDetails>>(){}.getType();
        try {
            List<TankMetaDetails> details = gson.fromJson(json, tankType);
            if(details == null) {
                return new ArrayList<>();
            }
            return details;
        } catch (Exception ex) {
            return new ArrayList<>();
        }
    }

    public void addTankMeta(TankMetaDetails details) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        List<TankMetaDetails> existingTankMetaDetailsList = getTankMetaDetails();

        existingTankMetaDetailsList.add(details);
        Gson gson = new Gson();
        String stringTankList = gson.toJson(existingTankMetaDetailsList);

        editor.putString("tank_details", stringTankList);
        editor.apply();
    }

    public void addTankValue(TankValueDetails details) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        List<TankValueDetails> existingTankValueDetailsList = getTankValueDetails();

        existingTankValueDetailsList.add(details);
        Gson gson = new Gson();
        String stringTankList = gson.toJson(existingTankValueDetailsList);

        editor.putString("tank_value_details", stringTankList);
        editor.apply();
    }

    public List<TankValueDetails> getTankValueDetails() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        String json = sharedPreferences.getString("tank_value_details", "");

        Gson gson = new Gson();
        Type tankType = new TypeToken<List<TankValueDetails>>(){}.getType();
        try {
            List<TankValueDetails> details = gson.fromJson(json, tankType);
            if(details == null) {
                return new ArrayList<>();
            }
            return details;
        } catch (Exception ex) {
            return new ArrayList<>();
        }
    }

    public List<TankValueDetails> getTankValueDetailsSingle() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        String json = sharedPreferences.getString("tank_value_details_single", "");

        Gson gson = new Gson();
        Type tankType = new TypeToken<List<TankValueDetails>>(){}.getType();
        try {
            List<TankValueDetails> details = gson.fromJson(json, tankType);
            if(details == null) {
                return new ArrayList<>();
            }
            return details;
        } catch (Exception ex) {
            return new ArrayList<>();
        }
    }

    public List<PumpValueDetails> getPumpValueDetailsSingle() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        String json = sharedPreferences.getString("pump_value_details_single", "");

        Gson gson = new Gson();
        Type pumpType = new TypeToken<List<PumpValueDetails>>(){}.getType();
        try {
            List<PumpValueDetails> details = gson.fromJson(json, pumpType);
            if(details == null) {
                return new ArrayList<>();
            }
            return details;
        } catch (Exception ex) {
            return new ArrayList<>();
        }
    }

    public String getDgInfo() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString("devid", "");
    }

    public void addTankValueSingle(TankValueDetails details) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        List<TankValueDetails> existingTankValueDetailsList = getTankValueDetailsSingle();

        existingTankValueDetailsList.add(details);
        Gson gson = new Gson();
        String stringTankList = gson.toJson(existingTankValueDetailsList);

        editor.putString("tank_value_details_single", stringTankList);
        editor.apply();
    }

    public void clearExistingTankValuesSingle() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.remove("tank_value_details_single");
        editor.apply();
    }

    public void addPumpValueSingle(PumpValueDetails details) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        List<PumpValueDetails> existingPumpValueDetailsList = getPumpValueDetailsSingle();

        existingPumpValueDetailsList.add(details);
        Gson gson = new Gson();
        String stringTankList = gson.toJson(existingPumpValueDetailsList);

        editor.putString("pump_value_details_single", stringTankList);
        editor.apply();
    }

    public void clearExistingPumpValuesSingle() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.remove("pump_value_details_single");
        editor.apply();
    }
    public void clearExistingTankValues() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.remove("tank_value_details");
        editor.apply();
    }

    public List<DgMetaDetails> getDgMetaDetails() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        String json = sharedPreferences.getString("dg_details", "");

        Gson gson = new Gson();
        Type dgType = new TypeToken<List<DgMetaDetails>>(){}.getType();
        try {
            List<DgMetaDetails> details = gson.fromJson(json, dgType);
            if(details == null) {
                return new ArrayList<>();
            }
            return details;
        } catch (Exception ex) {
            return new ArrayList<>();
        }
    }

    public void addDgMeta(DgMetaDetails details) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        List<DgMetaDetails> existingDgMetaDetailsList = getDgMetaDetails();

        existingDgMetaDetailsList.add(details);
        Gson gson = new Gson();
        String stringDgList = gson.toJson(existingDgMetaDetailsList);

        editor.putString("dg_details", stringDgList);
        editor.apply();
    }

    public List<DgValueDetails> getDgValueDetails() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        String json = sharedPreferences.getString("dg_value_details", "");

        Gson gson = new Gson();
        Type dgType = new TypeToken<List<DgValueDetails>>(){}.getType();
        try {
            List<DgValueDetails> details = gson.fromJson(json, dgType);
            if(details == null) {
                return new ArrayList<>();
            }
            return details;
        } catch (Exception ex) {
            return new ArrayList<>();
        }
    }

    public List<TankPlotDetails> getTankPlotDetails() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        String json = sharedPreferences.getString("tank_plot_details", "");

        Gson gson = new Gson();
        Type dgType = new TypeToken<List<TankPlotDetails>>(){}.getType();
        try {
            List<TankPlotDetails> details = gson.fromJson(json, dgType);
            if(details == null) {
                return new ArrayList<>();
            }
            return details;
        } catch (Exception ex) {
            return new ArrayList<>();
        }
    }

    public List<DgValueDetails> getDgValueDetailsSingle() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        String json = sharedPreferences.getString("dg_value_details_single", "");

        Gson gson = new Gson();
        Type dgType = new TypeToken<List<DgValueDetails>>(){}.getType();
        try {
            List<DgValueDetails> details = gson.fromJson(json, dgType);
            if(details == null) {
                return new ArrayList<>();
            }
            return details;
        } catch (Exception ex) {
            return new ArrayList<>();
        }
    }

    public void addDgValue(DgValueDetails details) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        List<DgValueDetails> existingDgValueDetailsList = getDgValueDetails();

        existingDgValueDetailsList.add(details);
        Gson gson = new Gson();
        String stringDgList = gson.toJson(existingDgValueDetailsList);

        editor.putString("dg_value_details", stringDgList);
        editor.apply();
    }

    public void addTankPlot(TankPlotDetails details) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        List<TankPlotDetails> existingTankPlotDetailsList = getTankPlotDetails();

        existingTankPlotDetailsList.add(details);
        Gson gson = new Gson();
        String stringDgList = gson.toJson(existingTankPlotDetailsList);

        editor.putString("tank_plot_details", stringDgList);
        editor.apply();
    }

    public void addDgValueSingle(DgValueDetails details) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        List<DgValueDetails> existingDgValueDetailsList = getDgValueDetailsSingle();

        existingDgValueDetailsList.add(details);
        Gson gson = new Gson();
        String stringDgList = gson.toJson(existingDgValueDetailsList);

        editor.putString("dg_value_details_single", stringDgList);
        editor.apply();
    }
    public void clearExistingDGValues() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.remove("dg_value_details");
        editor.apply();
    }

    public void clearExistingTankPlotValues() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.remove("plot_value_details");
        editor.apply();
    }

    public void clearExistingDGValuesSingle() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.remove("dg_value_details_single");
        editor.apply();
    }
    public void clearExistingDGs() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.remove("dg_details");
        editor.apply();
    }

    public void clear() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.clear();
        editor.apply();

    }

    public void storeDGName(Dglist dglist){

        System.out.println("came inside shared preference");
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        List<Dglist> existingDgDetailsList = getDgName();

        existingDgDetailsList.clear();

        existingDgDetailsList.add(dglist);

        System.out.println("existingdglist:"  + existingDgDetailsList);
        Gson gson = new Gson();
        String stringDgList = gson.toJson(existingDgDetailsList);
        System.out.println("stringDgList:"  + stringDgList);

 //       editor.clear();
        editor.putString("dgname", stringDgList);

        editor.apply();

    }

    public List<Dglist> getDgName() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
//        sharedPreferences.edit().remove("dgname").commit();

        String json = sharedPreferences.getString("dgname", "");

        Gson gson = new Gson();
        Type dgType = new TypeToken<List<Dglist>>(){}.getType();
        try {
            List<Dglist> details = gson.fromJson(json, dgType);
            if(details == null) {
                return new ArrayList<>();
            }
            return details;
        } catch (Exception ex) {
            return new ArrayList<>();
        }


    }

    public void clearExistingDgName() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.remove("dgname");
        editor.apply();
    }


}


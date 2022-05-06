package com.kfxlabs.smartsociety.storage;

import android.content.Context;
import android.content.SharedPreferences;

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
/*
    public void saveUser(User user){
*//*
        System.out.println("RN2611 id:" + user.getId());
        System.out.println("RN2611 email:" + user.getEmail());
        System.out.println("RN2611 name:" + user.getName());
        System.out.println("RN2611 school:" + user.getSchool());
*//*
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
         SharedPreferences.Editor editor = sharedPreferences.edit();

         editor.putInt("id", 123);
        editor.putString("email", "a@gmail.com");
        editor.putString("school", "Chris");
        editor.putString("name", "rucha");

        editor.apply();
    }*/

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

    public String getpassword() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString("password", "");
    }
/*    public void UserInfo(String userName, String phoneNumber, String emailId){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user_name", userName);
        editor.putString("phone_number", phoneNumber);
        editor.putString("email_id", emailId);
        editor.apply();
    }

    public InfoPost getUserInfo() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return new InfoPost(

                sharedPreferences.getString("user_name", "No User Name"),
                sharedPreferences.getString("phone_number",""),
                sharedPreferences.getString("email_id",""));


    }*/


    public boolean isLoggedIn(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getInt("id",-1)!= -1;

    }
/*
    public User getUser(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return new User(


                    sharedPreferences.getString("email",null),
                    sharedPreferences.getString("school",null),
                    sharedPreferences.getString("name",null)

            );
        }*/

        public void clear() {
            SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.clear();
            editor.apply();

        }
}


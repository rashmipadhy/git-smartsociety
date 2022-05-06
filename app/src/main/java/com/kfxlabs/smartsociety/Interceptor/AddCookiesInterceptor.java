package com.kfxlabs.smartsociety.Interceptor;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.kfxlabs.smartsociety.storage.SharedPrefManager;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AddCookiesInterceptor implements Interceptor {

    public static final String PREF_COOKIES = "PREF_COOKIES";
    private static final String SHARED_PREF_NAME = "my_shared_pref";
    private Context context;

    public AddCookiesInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();

        /*HashSet<String> preferences = (HashSet<String>)context.getSharedPreferences("my_shared_pref",Context.MODE_PRIVATE)
                .getStringSet(PREF_COOKIES, new HashSet<String>());*/
        /*HashSet<String> cookies = (HashSet<String>) PreferenceManager.getDefaultSharedPreferences(context).
                getStringSet("PREF_COOKIES", new HashSet<String>());*/

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        HashSet<String> cookies = (HashSet<String>) sharedPreferences.getStringSet(PREF_COOKIES, new HashSet<String>());

        Request original = chain.request();
        if (cookies != null) {
            for (String cookie : cookies) {
                builder.addHeader("Cookie", cookie);
            }

        }
        return chain.proceed(builder.build());
    }
}

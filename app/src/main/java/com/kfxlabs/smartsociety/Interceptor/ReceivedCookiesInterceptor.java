package com.kfxlabs.smartsociety.Interceptor;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Response;


public class ReceivedCookiesInterceptor implements Interceptor {

    public static final String PREF_COOKIES = "PREF_COOKIES";
    private static final String SHARED_PREF_NAME = "my_shared_pref";
    private Context context;

    public ReceivedCookiesInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());


        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            /*HashSet<String> cookies = (HashSet<String>) context.getSharedPreferences("my_shared_pref", Context.MODE_PRIVATE)
                    .getStringSet("PREF_COOKIES", new HashSet<String>());*/

            SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
            HashSet<String> cookies = (HashSet<String>) sharedPreferences.getStringSet(PREF_COOKIES, new HashSet<String>());


            for (String header : originalResponse.headers("Set-Cookie")) {
                cookies.add(header);
            }

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putStringSet(PREF_COOKIES, cookies).apply();
        }

        return originalResponse;
    }
}


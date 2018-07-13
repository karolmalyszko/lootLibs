package io.loot.lootsdk.networking.interceptors;

import android.util.Base64;

import java.io.IOException;

import io.loot.lootsdk.networking.interceptors.models.LootHeader;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


public class HeaderInterceptor extends BaseInterceptor {


    private String mAuthUsername;
    private String mAuthPassword;
    private String mAnalyticsDataBase64;

    public HeaderInterceptor(LootHeader header) {
        mAuthUsername = header.getAuthUsername();
        mAuthPassword = header.getAuthPassword();
        mAnalyticsDataBase64 = header.getAnalyticsDataBase64();
    }

    @Override
    public Response interceptRequest(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();

        String credentials = mAuthUsername + ":" + mAuthPassword;

        request = request
                .newBuilder()
                .addHeader("Authorization", "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP))
                .addHeader("platform", "android")
                .addHeader("Analytics-Data", mAnalyticsDataBase64)
                .build();

        return chain.proceed(request);
    }
}

package io.loot.lootsdk.networking.interceptors;

import android.util.Base64;

import java.io.IOException;

import io.loot.lootsdk.networking.interceptors.models.LootHeader;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class PlatformAndTokenHeaderInterceptor extends BaseInterceptor {

    private String mOnboardingToken;
    private String mAuthUsername;
    private String mAuthPassword;
    private String mAnalyticsDataBase64;
    private String mUserEmail;

    public PlatformAndTokenHeaderInterceptor(LootHeader header) {
        mOnboardingToken = header.getOnboardingToken();
        mAuthUsername = header.getAuthUsername();
        mAuthPassword = header.getAuthPassword();
        mUserEmail = header.getEmail();
        mAnalyticsDataBase64 = header.getAnalyticsDataBase64();
    }

    @Override
    public Response interceptRequest(Interceptor.Chain chain) throws IOException {

        String credentials = mAuthUsername + ":" + mAuthPassword;

        Request request = chain.request();
        request = request.newBuilder()
                .addHeader("Authorization", "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP))
                .addHeader("email", mUserEmail)
                .addHeader("token", mOnboardingToken)
                .addHeader("platform", "android")
                .addHeader("Analytics-Data", mAnalyticsDataBase64)
                .build();

        return chain.proceed(request);
    }
}
package io.loot.lootsdk.networking.interceptors;

import android.util.Base64;

import java.io.IOException;

import io.loot.lootsdk.networking.interceptors.models.LootHeader;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class TokenHeaderInterceptor extends BaseInterceptor {

    private String mAuthUsername;
    private String mAuthPassword;
    private String mEmail;
    private String mAuthToken;
    private String mAnalyticsDataBase64;

    public TokenHeaderInterceptor(LootHeader header) {
        mAuthUsername = header.getAuthUsername();
        mAuthPassword = header.getAuthPassword();


        if (header.getAuthToken() != null && !header.getAuthToken().isEmpty()) {
            mAuthToken = header.getAuthToken();
        } else {
            mAuthToken = header.getOnboardingToken();
        }

        mEmail = header.getEmail();
        mAnalyticsDataBase64 = header.getAnalyticsDataBase64();
    }

    @Override
    public Response interceptRequest(Interceptor.Chain chain) throws IOException {

        String credentials = mAuthUsername + ":" + mAuthPassword;

        Request request = chain.request();
        request = request.newBuilder()
                .addHeader("email", mEmail)
                .addHeader("token", mAuthToken)
                .addHeader("Referer", "api.lootapp.io")
                .addHeader("platform", "android")
                .addHeader("Authorization", "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP))
                .addHeader("Analytics-Data", mAnalyticsDataBase64)
                .build();

        if (mAuthToken == null || mAuthToken.isEmpty()) {
            chain.call().cancel();
        }

        return chain.proceed(request);
    }
}

package io.loot.lootsdk.networking.interceptors;

import android.support.annotation.NonNull;

import org.joda.time.DateTime;

import java.io.IOException;

import io.loot.lootsdk.LootSDK;
import okhttp3.Interceptor;
import okhttp3.Response;

public abstract class BaseInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = interceptRequest(chain);
        try {
            LootSDK.getInstance().serverInfo().setServerDate(getServerDate(response));
        }
        catch (Exception e) {}
        return response;
    }

    public abstract Response interceptRequest(Chain chain) throws IOException;

    private String getServerDate(Response response) {
        return new DateTime(response.headers().getDate("date")).toString();
    }
}

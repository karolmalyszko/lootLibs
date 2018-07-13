package io.loot.lootsdk.networking.interceptors;

import android.content.Context;

import io.loot.lootsdk.networking.interceptors.models.LootHeader;
import okhttp3.Interceptor;

public class InterceptorFactory {

    public static Interceptor getInterceptor(InterceptorType type, Context context, LootHeader header) {
        switch (type) {
            case NO_TOKEN:
                return new HeaderInterceptor(header);
            case SESSION_TOKEN:
                return new TokenHeaderInterceptor(header);
            case PLATFORM_HEADER:
                return new PlatformHeaderInterceptor(header);
            case PLATFORM_AND_TOKEN_HEADER:
                return new PlatformAndTokenHeaderInterceptor(header);
            case OFFLINE:
                return new OfflineInterceptor(context);
        }
        return  new HeaderInterceptor(header);
    }
}

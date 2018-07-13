package io.loot.lootsdk.networking.restClients;

import android.content.Context;

import io.loot.lootsdk.LootSDK;
import io.loot.lootsdk.networking.interceptors.InterceptorType;
import io.loot.lootsdk.networking.interceptors.models.LootHeader;

public class RestClientFactory {

    public static RestClient createRestClient(int apiType, InterceptorType type, Context context, LootHeader header) {
        switch (apiType) {
            case LootSDK.API_TYPE_STAGING:
                return new StagingRestClient(context, type, header);
            case LootSDK.API_TYPE_PRODUCTION:
                return new ProductionRestClient(context, type, header);
            case LootSDK.API_TYPE_OFFLINE:
                return new OfflineRestClient(context, type, header);
            default:
                return new StagingRestClient(context, type, header); // staging by default
        }
    }
}

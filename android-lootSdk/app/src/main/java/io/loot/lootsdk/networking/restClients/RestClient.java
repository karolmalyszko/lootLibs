package io.loot.lootsdk.networking.restClients;

import android.content.Context;

import io.loot.lootsdk.networking.LootApiInterface;
import io.loot.lootsdk.networking.interceptors.InterceptorType;
import io.loot.lootsdk.networking.interceptors.models.LootHeader;

public class RestClient {
    private LootApiInterface mLootApiInterface;

    public RestClient(Context context, InterceptorType type, LootHeader header) {

    }

    public LootApiInterface getApiService() {
        return mLootApiInterface;
    }
}

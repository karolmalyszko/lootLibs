package io.loot.lootsdk.networking.restClients;

import android.content.Context;

import java.util.concurrent.TimeUnit;

import io.loot.lootsdk.LootSDK;
import io.loot.lootsdk.networking.LootApiInterface;
import io.loot.lootsdk.networking.interceptors.InterceptorFactory;
import io.loot.lootsdk.networking.interceptors.InterceptorType;
import io.loot.lootsdk.networking.interceptors.models.LootHeader;
import io.loot.lootsdk.utils.LiveDataCallAdapterFactory;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductionRestClient extends RestClient{
    private static int TIMEOUT_LIMIT = 60;

    private LootApiInterface mLootApiInterface;

    public ProductionRestClient(Context context, InterceptorType type, LootHeader header) {
        super(context, type, header);
        OkHttpClient client;
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
            }
        });
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        client = new OkHttpClient.Builder().writeTimeout(TIMEOUT_LIMIT, TimeUnit.SECONDS).readTimeout(TIMEOUT_LIMIT, TimeUnit.SECONDS).connectTimeout(TIMEOUT_LIMIT, TimeUnit.SECONDS).addInterceptor(logging).addInterceptor(InterceptorFactory.getInterceptor(type, context, header)).build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(LootSDK.PRODUCTION_URI).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(new LiveDataCallAdapterFactory()).client(client).build();

        mLootApiInterface = retrofit.create(LootApiInterface.class);
    }

    public LootApiInterface getApiService() {
        return mLootApiInterface;
    }
}

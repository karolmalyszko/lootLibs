package io.loot.lootsdk.networking.restClients;

import android.content.Context;
import android.util.Log;

import io.loot.lootsdk.LootSDK;
import io.loot.lootsdk.networking.LootApiInterface;
import io.loot.lootsdk.networking.interceptors.InterceptorFactory;
import io.loot.lootsdk.networking.interceptors.InterceptorType;
import io.loot.lootsdk.networking.interceptors.models.LootHeader;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OfflineRestClient  extends RestClient{


    private LootApiInterface mLootApiInterface;

    public OfflineRestClient(Context context, InterceptorType type, LootHeader header) {
        super(context, type, header);
        OkHttpClient client;
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d("Http", message);
            }
        });
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        client = new OkHttpClient.Builder().addInterceptor(logging).addInterceptor(InterceptorFactory.getInterceptor(InterceptorType.OFFLINE, context, header)).build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(LootSDK.OFFLINE_URI).addConverterFactory(GsonConverterFactory.create()).client(client).build();

        mLootApiInterface = retrofit.create(LootApiInterface.class);
    }

    public LootApiInterface getApiService() {
        return mLootApiInterface;
    }
}

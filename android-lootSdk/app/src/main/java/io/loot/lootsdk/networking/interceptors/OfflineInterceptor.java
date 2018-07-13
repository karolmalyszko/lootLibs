package io.loot.lootsdk.networking.interceptors;


import android.content.Context;

import java.io.IOException;

import io.loot.lootsdk.networking.OfflineResponseStringBuilder;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class OfflineInterceptor implements Interceptor {

    private Context mContext;


    public OfflineInterceptor(Context context) {
        mContext = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        String responseString = "";
        int responseCode = 200;
       responseString = OfflineResponseStringBuilder.getResponse(mContext, chain);
        if (responseString.equals(OfflineResponseStringBuilder.ERROR422)){
            responseCode = 422;
            responseString = "";
        } else if (responseString.replaceAll("\"error\": \".*\"","\"error\": \"ERROR_CODE\"").equals(OfflineResponseStringBuilder.loadJSONFromAsset("error", mContext))) {
            responseCode = 422;
        }
        Response response = new Response.Builder()
                .code(responseCode)
                .message(responseString)
                .request(chain.request())
                .protocol(Protocol.HTTP_1_0)
                .body(ResponseBody.create(MediaType.parse("application/json"), responseString.getBytes()))
                .addHeader("content-type", "application/json")
                .build();

        return response;
    }
}

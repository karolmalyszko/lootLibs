package io.loot.lootsdk.networking;

import android.support.annotation.Nullable;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import io.loot.lootsdk.models.networking.ErrorExtractor;
import retrofit2.Response;

import static io.loot.lootsdk.models.networking.ErrorExtractor.NO_INTERNET_CONNECTION;
import static io.loot.lootsdk.models.networking.ErrorExtractor.UNEXPECTED_ERROR;

public class ApiResponse<T> {

    private int mCode;

    @Nullable
    private T mBody;

    @Nullable
    private String mErrorMessage;

    @Nullable
    private Throwable mThrowable;

    public ApiResponse(Throwable error) {
        mCode = 500;
        mBody = null;
        mThrowable = error;
        mErrorMessage = error.getMessage();
    }

    public ApiResponse(Response<T> response) {
        mCode = response.code();

        if(response.isSuccessful()) {
            mBody = response.body();
            mErrorMessage = null;
        } else {
            String message = null;
            if (response.errorBody() != null) {
                try {
                    message = response.errorBody().string();
                } catch (IOException ignored) {
                    ignored.printStackTrace();
                }
            }
            if (message == null || message.trim().length() == 0) {
                message = response.message();
            }
            mErrorMessage = message;
            mBody = null;
        }
    }

    public T getBody() {
        return mBody;
    }

    public String getRawErrorMessage() {
        if (mThrowable != null ) {
            if (mThrowable instanceof UnknownHostException || mThrowable instanceof TimeoutException || mThrowable instanceof ConnectException || mThrowable instanceof SocketException) {
                return NO_INTERNET_CONNECTION;
            }

            return UNEXPECTED_ERROR;
        }

        return mErrorMessage;
    }

    public String getErrorMessage() {
        return (mThrowable != null) ? getRawErrorMessage() : ErrorExtractor.extract(mCode, getRawErrorMessage());
    }

    public int getCode() {
        return mCode;
    }

    public Throwable getThrowable() {
        return mThrowable;
    }

    public boolean isSuccessful() {
        return mCode >= 200 && mCode < 300;
    }

}

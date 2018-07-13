package io.loot.lootsdk.models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.loot.lootsdk.models.networking.ErrorExtractor;

import static io.loot.lootsdk.models.Resource.Status.ERROR;
import static io.loot.lootsdk.models.Resource.Status.LOADING;
import static io.loot.lootsdk.models.Resource.Status.SUCCESS;
import static io.loot.lootsdk.models.networking.ErrorExtractor.FAILURE;

public class Resource<T> {

    @NonNull
    private final Status status;
    @Nullable
    private final String message;
    @Nullable
    private final T data;
    private final int code;

    private Resource(@NonNull Status status, @Nullable T data, @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
        this.code = -1;
    }

    private Resource(@NonNull Status status, @Nullable T data, @Nullable String message, int code) {
        this.status = status;
        this.data = data;
        this.message = message;
        this.code = code;
    }

    public static <T> Resource<T> success(@Nullable T data) {
        return new Resource<>(SUCCESS, data, null);
    }

    public static <T> Resource<T> success(@Nullable T data, int code) {
        return new Resource<>(SUCCESS, data, null, code);
    }

    @Deprecated
    public static <T> Resource<T> error(String msg, @Nullable T data) {
        return new Resource<>(ERROR, data, msg);
    }

    public static <T> Resource<T> error(String msg, int code, @Nullable T data) {
        return new Resource<>(ERROR, data, msg, code);
    }

    public static <T> Resource<T> loading(@Nullable T data) {
        return new Resource<>(LOADING, data, null);
    }

    public boolean isLive() {
        return status == SUCCESS && data != null;
    }

    public boolean isCached() {
        return status == LOADING && data != null ;
    }

    public boolean isSuccessful() {
        return status != ERROR;
    }

    public boolean isLoading() {
        return status == LOADING;
    }

    public String getErrorMessage() {
        if (FAILURE.equals(message)) {
            return message;
        }


        return ErrorExtractor.extract(code, message);
    }

    public String getRawError() {
        return message;
    }

    public int getResponseCode() {
        return this.code;
    }

    @Nullable
    public T getData() {
        return data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Resource<?> resource = (Resource<?>) o;
        return status == resource.status && (message != null ? message.equals(resource.message) : resource.message == null) && (data != null ? data.equals(resource.data) : resource.data == null);
    }

    @Override
    public int hashCode() {
        int result = status.hashCode();
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (data != null ? data.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Resource{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public enum Status {
        LOADING, ERROR, SUCCESS
    }

}

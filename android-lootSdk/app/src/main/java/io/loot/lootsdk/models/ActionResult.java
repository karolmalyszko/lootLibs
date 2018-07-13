package io.loot.lootsdk.models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static io.loot.lootsdk.models.Resource.Status.ERROR;
import static io.loot.lootsdk.models.Resource.Status.LOADING;
import static io.loot.lootsdk.models.Resource.Status.SUCCESS;

public class ActionResult {

    public enum Status {
        LOADING, ERROR, SUCCESS
    }

    @NonNull
    private final Status status;

    private final String message;

    private ActionResult(@NonNull Status status, @Nullable String message) {
        this.status = status;
        this.message = message;
    }

    public static ActionResult success() {
        return new ActionResult(Status.SUCCESS, "");
    }

    public static ActionResult error(String message) {
        return new ActionResult(Status.ERROR, message);
    }

    public static ActionResult loading() {
        return new ActionResult(Status.LOADING, "");
    }

    public boolean isError() {
        return status == Status.ERROR;
    }

    public boolean isLoading() {
        return status == Status.LOADING;
    }

    public boolean isSuccess() {
        return status == Status.SUCCESS;
    }

    public String getErrorMessage() {
        return message != null ? message : "";
    }


    @Override
    public String toString() {
        return "ActionResult{" +
                "status=" + status +
                ", message='" + message + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        return getClass().toString().equals(obj.getClass().toString()) && toString().equals(obj.toString());

    }
}

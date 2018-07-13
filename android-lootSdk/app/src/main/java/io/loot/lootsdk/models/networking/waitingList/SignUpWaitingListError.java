package io.loot.lootsdk.models.networking.waitingList;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.loot.lootsdk.models.networking.ErrorExtractor;
import lombok.Data;

public @Data class SignUpWaitingListError implements Serializable{

    @SerializedName("error")
    Error errorCode;

    public boolean isAlreadyOnWaitingList() {
        return errorCode.getCode() == 1;
    }

    public boolean isRegularAccount() {
        return errorCode.getCode() == 2;
    }

    public String getErrorTextMessage() {
        switch (errorCode.getCode()) {
            case 1:
                return "EMAIL_EXISTS";
            case 2:
                return "REGISTERED_AS_REGULAR_ACCOUNT";
            case 3:
                return "EMAIL_INVALID";
            case 4:
                return "REFERRAL_CODE";
        }

        return ErrorExtractor.UNEXPECTED_ERROR;
    }
}

@Data class Error {
    @SerializedName("code")
    private int code;
    @SerializedName("error")
    private String error;
}
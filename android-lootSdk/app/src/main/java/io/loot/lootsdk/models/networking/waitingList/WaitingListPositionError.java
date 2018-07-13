package io.loot.lootsdk.models.networking.waitingList;


import android.content.Context;

import io.loot.lootsdk.models.networking.ErrorExtractor;
import lombok.Data;

public @Data class WaitingListPositionError {

    private int code;

    public String getErrorCodeMessage(Context context) {
        switch (code) {
            case 1:
                return "INVALID_TOKEN";
            case 2:
                return "INVALID_EMAIL";
        }

        return ErrorExtractor.UNEXPECTED_ERROR;
    }
}

package io.loot.lootsdk.models.networking.sessions;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Data;

public @Data class AccountStatusResponse implements Serializable {

    @SerializedName("status")
    String status;

}

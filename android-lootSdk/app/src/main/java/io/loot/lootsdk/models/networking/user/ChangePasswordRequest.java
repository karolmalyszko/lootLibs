package io.loot.lootsdk.models.networking.user;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

public @Data class ChangePasswordRequest {

    @SerializedName("password")
    private String password;
    @SerializedName("new_password")
    private String newPassword;
}
package io.loot.lootsdk.models.networking.sessions;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

public @Data class LoginRequest {

    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

}

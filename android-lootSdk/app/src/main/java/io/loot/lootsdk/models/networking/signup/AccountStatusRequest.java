package io.loot.lootsdk.models.networking.signup;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Data;

public @Data class AccountStatusRequest implements Serializable {

    @SerializedName("email")
    String email;
    @SerializedName("password")
    String password;

    public AccountStatusRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

}

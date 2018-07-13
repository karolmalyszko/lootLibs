package io.loot.lootsdk.models.networking.authToken;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Data;

@Data
public class AuthTokenResponse implements Serializable {

    @SerializedName("auth_token")
    String authToken;

}

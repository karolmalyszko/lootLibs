package io.loot.lootsdk.models.networking.forgottenPassword;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class ForgotPasswordConfirmRequest {
    @SerializedName("token")
    String token;
    @SerializedName("code")
    String code;
}

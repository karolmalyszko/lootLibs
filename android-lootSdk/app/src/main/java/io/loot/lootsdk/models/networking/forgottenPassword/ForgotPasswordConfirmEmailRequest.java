package io.loot.lootsdk.models.networking.forgottenPassword;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class ForgotPasswordConfirmEmailRequest {
    @SerializedName("token")
    String token;
    @SerializedName("email")
    String email;
}

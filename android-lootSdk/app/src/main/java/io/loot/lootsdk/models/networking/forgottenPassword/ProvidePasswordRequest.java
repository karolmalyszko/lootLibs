package io.loot.lootsdk.models.networking.forgottenPassword;


import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class ProvidePasswordRequest {
    @SerializedName("email")
    String email;
    @SerializedName("new_password")
    String password;
}

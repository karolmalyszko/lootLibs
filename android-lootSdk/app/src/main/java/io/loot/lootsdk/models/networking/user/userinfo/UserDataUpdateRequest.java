package io.loot.lootsdk.models.networking.user.userinfo;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

public @Data class UserDataUpdateRequest {

    @SerializedName("personal_details")
    private PersonalDetailsResponse personalDetails;

}

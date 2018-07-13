package io.loot.lootsdk.models.networking.user.userinfo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserResponse implements Serializable {

    @SerializedName("user")
    UserInfoResponse userInfo;

}

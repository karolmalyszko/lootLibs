package io.loot.lootsdk.models.networking.user.userinfo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.loot.lootsdk.models.data.userinfo.UserInfo;
import io.loot.lootsdk.models.orm.UserInfoEntity;
import lombok.Data;

public @Data class UserInfoResponse implements Serializable {

    @SerializedName("email")
    private String email;

    @SerializedName("current_country_code")
    private String countryCode;

    @SerializedName("personal_details")
    private PersonalDetailsResponse personalDetails;

    @SerializedName("legend")
    private boolean legend;

    public static UserInfoEntity parseToEntityObject(UserInfoResponse userInfoResponse) {
        if (userInfoResponse == null) {
            userInfoResponse = new UserInfoResponse();
        }
        UserInfoEntity userInfoEntity = new UserInfoEntity();
        userInfoEntity.setEmail(userInfoResponse.getEmail());
        userInfoEntity.setCountryCode(userInfoResponse.getCountryCode());
        userInfoEntity.setPersonalDetails(PersonalDetailsResponse.parseToEntityObject(userInfoResponse.getPersonalDetails()));
        userInfoEntity.setLegend(userInfoResponse.isLegend());
        return userInfoEntity;
    }

    public static UserInfo parseToDataObject(UserInfoResponse userInfoResponse) {
        if (userInfoResponse == null) {
            userInfoResponse = new UserInfoResponse();
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setEmail(userInfoResponse.getEmail());
        userInfo.setCountryCode(userInfoResponse.getCountryCode());
        userInfo.setPersonalDetails(PersonalDetailsResponse.parseToDataObject(userInfoResponse.getPersonalDetails()));
        userInfo.setLegend(userInfoResponse.isLegend());
        return userInfo;
    }

}

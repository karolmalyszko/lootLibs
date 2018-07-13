package io.loot.lootsdk.models.networking.signup;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.loot.lootsdk.models.networking.user.userinfo.AddressResponse;
import lombok.Data;

public @Data class OnBoardingPersonalDataRequest implements Serializable {

    @SerializedName("prefered_first_name")
    String preferedFirstName;

    @SerializedName("prefered_last_name")
    String prefredLastName;

    @SerializedName("title")
    String title;

    @SerializedName("gender")
    String gender;

    @SerializedName("address")
    AddressResponse address;

}

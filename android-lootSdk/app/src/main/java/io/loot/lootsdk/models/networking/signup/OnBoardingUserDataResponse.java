package io.loot.lootsdk.models.networking.signup;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.loot.lootsdk.models.data.userinfo.OnBoardingUserData;
import lombok.Data;

public @Data class OnBoardingUserDataResponse implements Serializable {

    @SerializedName("email")
    private String email;
    @SerializedName("phone_number")
    private String phoneNumber;
    @SerializedName("last_finished_step")
    private String lastFinishedStep;
    @SerializedName("personal_data")
    private PersonalDataResponse personalData;
    @SerializedName("kyc")
    private KycResponse kyc;
    @SerializedName("token")
    private String token;
    @SerializedName("intercom_hash")
    private String intercomHash;
    @SerializedName("public_id")
    private String publicId;

    public static OnBoardingUserData parseToDataObject(OnBoardingUserDataResponse onBoardingUserDataResponse) {
        if (onBoardingUserDataResponse == null) {
            onBoardingUserDataResponse = new OnBoardingUserDataResponse();
        }
        OnBoardingUserData onBoardingUserData = new OnBoardingUserData();
        onBoardingUserData.setEmail(onBoardingUserDataResponse.getEmail());
        onBoardingUserData.setPhoneNumber(onBoardingUserDataResponse.getPhoneNumber());
        onBoardingUserData.setLastFinishedStep(onBoardingUserDataResponse.getLastFinishedStep());
        onBoardingUserData.setPersonalData(PersonalDataResponse.parseToDataObject(onBoardingUserDataResponse.getPersonalData()));
        onBoardingUserData.setKyc(KycResponse.parseToDataObject(onBoardingUserDataResponse.getKyc()));
        onBoardingUserData.setToken(onBoardingUserDataResponse.getToken());
        onBoardingUserData.setIntercomHash(onBoardingUserDataResponse.getIntercomHash());
        onBoardingUserData.setPublicId(onBoardingUserDataResponse.getPublicId());

        return onBoardingUserData;
    }

}

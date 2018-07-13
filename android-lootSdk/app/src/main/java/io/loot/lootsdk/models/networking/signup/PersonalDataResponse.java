package io.loot.lootsdk.models.networking.signup;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.loot.lootsdk.models.data.userinfo.PersonalData;
import lombok.Data;


public @Data class PersonalDataResponse implements Serializable {

    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    @SerializedName("preferred_name")
    private String preferredFistName;
    @SerializedName("birth_date")
    private String birthDate;
    @SerializedName("preferred_last_name")
    private String preferredLastName;
    @SerializedName("title")
    private String title;
    @SerializedName("gender")
    private String gender;
    @SerializedName("address")
    private OnBoardingAddressResponse address;
    @SerializedName("profile_photo")
    String profilePhotoUrl;

    public static PersonalData parseToDataObject(PersonalDataResponse personalDataResponse) {
        if (personalDataResponse == null) {
            personalDataResponse = new PersonalDataResponse();
        }
        PersonalData personalData = new PersonalData();

        personalData.setFirstName(personalDataResponse.getFirstName());
        personalData.setLastName(personalDataResponse.getLastName());
        personalData.setPreferredFistName(personalDataResponse.getPreferredFistName());
        personalData.setBirthDate(personalDataResponse.getBirthDate());
        personalData.setPreferredLastName(personalDataResponse.getPreferredLastName());
        personalData.setTitle(personalDataResponse.getTitle());
        personalData.setGender(personalDataResponse.getGender());
        personalData.setAddress(OnBoardingAddressResponse.parseToDataObject(personalDataResponse.getAddress()));
        personalData.setProfilePhotoUrl(personalDataResponse.getProfilePhotoUrl());

        return personalData;
    }

    public static PersonalDataResponse parseToResponse(PersonalData personalData) {
        PersonalDataResponse response = new PersonalDataResponse();
        if (personalData == null) {
            personalData = new PersonalData();
        }
        response.setFirstName(personalData.getFirstName());
        response.setLastName(personalData.getLastName());
        response.setPreferredFistName(personalData.getPreferredFistName());
        response.setBirthDate(personalData.getBirthDate());
        response.setPreferredLastName(personalData.getPreferredLastName());
        response.setTitle(personalData.getTitle());
        response.setGender(personalData.getGender());
        response.setAddress(OnBoardingAddressResponse.parseToResponseObject(personalData.getAddress()));
        response.setProfilePhotoUrl(personalData.getProfilePhotoUrl());

        return response;
    }
}

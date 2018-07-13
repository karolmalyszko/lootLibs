package io.loot.lootsdk.models.networking.user.userinfo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.loot.lootsdk.models.data.userinfo.PersonalDetails;
import io.loot.lootsdk.models.orm.PersonalDetailsEntity;
import lombok.Data;

public @Data class PersonalDetailsResponse implements Serializable {

    @SerializedName("title")
    private String titleCode;

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("middle_name")
    private String middleName;

    @SerializedName("birth_date")
    private String birthDate;

    @SerializedName("gender")
    private String gender;

    @SerializedName("phone_number")
    private String mobileNumber;

    @SerializedName("address")
    private AddressResponse address;

    @SerializedName("preferred_name")
    private String preferredName;

    @SerializedName("profile_photo")
    private String profilePhoto;

    public static PersonalDetails parseToDataObject(PersonalDetailsResponse personalDetailsResponse) {
        if (personalDetailsResponse == null) {
            personalDetailsResponse = new PersonalDetailsResponse();
        }
        PersonalDetails personalDetails = new PersonalDetails();
        personalDetails.setTitleCode(personalDetailsResponse.getTitleCode());
        personalDetails.setFirstName(personalDetailsResponse.getFirstName());
        personalDetails.setLastName(personalDetailsResponse.getLastName());
        personalDetails.setMiddleName(personalDetailsResponse.getMiddleName());
        personalDetails.setBirthDate(personalDetailsResponse.getBirthDate());
        personalDetails.setGender(personalDetailsResponse.getGender());
        personalDetails.setMobileNumber(personalDetailsResponse.getMobileNumber());
        personalDetails.setAddress(AddressResponse.parseToDataObject(personalDetailsResponse.getAddress()));
        personalDetails.setPreferredName(personalDetailsResponse.getPreferredName());
        personalDetails.setProfilePhoto(personalDetailsResponse.getProfilePhoto());
        return personalDetails;
    }

    public static PersonalDetailsEntity parseToEntityObject(PersonalDetailsResponse personalDetailsResponse) {
        if (personalDetailsResponse == null) {
            personalDetailsResponse = new PersonalDetailsResponse();
        }
        PersonalDetailsEntity personalDetailsEntity = new PersonalDetailsEntity();
        personalDetailsEntity.setTitleCode(personalDetailsResponse.getTitleCode());
        personalDetailsEntity.setFirstName(personalDetailsResponse.getFirstName());
        personalDetailsEntity.setLastName(personalDetailsResponse.getLastName());
        personalDetailsEntity.setMiddleName(personalDetailsResponse.getMiddleName());
        personalDetailsEntity.setBirthDate(personalDetailsResponse.getBirthDate());
        personalDetailsEntity.setGender(personalDetailsResponse.getGender());
        personalDetailsEntity.setMobileNumber(personalDetailsResponse.getMobileNumber());
        personalDetailsEntity.setAddress(AddressResponse.parseToEntityObject(personalDetailsResponse.getAddress()));
        personalDetailsEntity.setPreferredName(personalDetailsResponse.getPreferredName());
        personalDetailsEntity.setProfilePhoto(personalDetailsResponse.getProfilePhoto());
        return personalDetailsEntity;
    }
}

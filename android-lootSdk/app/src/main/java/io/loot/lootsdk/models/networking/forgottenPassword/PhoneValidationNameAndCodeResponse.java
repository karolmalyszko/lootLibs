package io.loot.lootsdk.models.networking.forgottenPassword;

import com.google.gson.annotations.SerializedName;

import io.loot.lootsdk.models.data.forgottenPassword.PhoneValidationNameAndCode;

public class PhoneValidationNameAndCodeResponse {

    @SerializedName("validation_name")
    String validationName;
    @SerializedName("validation_code")
    int validationCode;
    @SerializedName("phone_number")
    String phoneNumber;

    public String getValidationName() {
        return validationName;
    }

    public void setValidationName(String validationName) {
        this.validationName = validationName;
    }

    public int getValidationCode() {
        return validationCode;
    }

    public void setValidationCode(int validationCode) {
        this.validationCode = validationCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public static PhoneValidationNameAndCode parseToDataObject(PhoneValidationNameAndCodeResponse phoneValidationNameAndCodeResponse) {
        if (phoneValidationNameAndCodeResponse == null) {
            phoneValidationNameAndCodeResponse = new PhoneValidationNameAndCodeResponse();
        }
        PhoneValidationNameAndCode phoneValidationNameAndCode = new PhoneValidationNameAndCode();
        phoneValidationNameAndCode.setValidationName(phoneValidationNameAndCodeResponse.getValidationName());
        phoneValidationNameAndCode.setValidationCode(phoneValidationNameAndCodeResponse.getValidationCode());
        phoneValidationNameAndCode.setPhoneNumber(phoneValidationNameAndCodeResponse.getPhoneNumber());
        return phoneValidationNameAndCode;
    }
}

package io.loot.lootsdk.models.data.forgottenPassword;

public class PhoneValidationNameAndCode {
    String validationName;
    int validationCode;
    String phoneNumber;

    public PhoneValidationNameAndCode() {
        this.validationName = "";
        this.validationCode = 0;
        this.phoneNumber = "";
    }

    public PhoneValidationNameAndCode(PhoneValidationNameAndCode phoneValidationNameAndCode) {
        this.validationName = phoneValidationNameAndCode.getValidationName();
        this.validationCode = phoneValidationNameAndCode.getValidationCode();
        this.phoneNumber = phoneValidationNameAndCode.getPhoneNumber();
    }

    public String getValidationName() {
        return validationName;
    }

    public void setValidationName(String validationName) {
        if (validationName == null) {
            validationName = "";
        }
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
        if (phoneNumber == null) {
            phoneNumber = "";
        }
        this.phoneNumber = phoneNumber;
    }
}

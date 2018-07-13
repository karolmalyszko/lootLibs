package io.loot.lootsdk.models.data.userinfo;

import java.io.Serializable;

public class PersonalDetails implements Serializable {

    private String titleCode;
    private String firstName;
    private String lastName;
    private String middleName;
    private String birthDate;
    private String gender;
    private String mobileNumber;
    private Address address;
    private String preferredName;
    private String profilePhoto;

    public PersonalDetails() {
        this.titleCode = "";
        this.firstName = "";
        this.lastName = "";
        this.middleName = "";
        this.birthDate = "";
        this.gender = "";
        this.mobileNumber = "";
        this.address = new Address();
        this.preferredName = "";
        this.profilePhoto = "";
    }

    public PersonalDetails(PersonalDetails personalDetails) {
        this.titleCode = personalDetails.getTitleCode();
        this.firstName = personalDetails.getFirstName();
        this.lastName = personalDetails.getLastName();
        this.middleName = personalDetails.getMiddleName();
        this.birthDate = personalDetails.getBirthDate();
        this.gender = personalDetails.getGender();
        this.mobileNumber = personalDetails.getMobileNumber();
        this.address = new Address(personalDetails.getAddress());
        this.preferredName = personalDetails.getPreferredName();
        this.profilePhoto = personalDetails.getProfilePhoto();
    }

    public String getTitleCode() {
        return titleCode;
    }

    public void setTitleCode(String titleCode) {
        if(titleCode == null) {
            titleCode = "";
        }
        this.titleCode = titleCode;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if(firstName == null) {
            firstName = "";
        }
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if(lastName == null) {
            lastName = "";
        }
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        if(middleName == null) {
            middleName = "";
        }
        this.middleName = middleName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        if(birthDate == null) {
            birthDate = "";
        }
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        if(gender == null) {
            gender = "";
        }
        this.gender = gender;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        if(mobileNumber == null) {
            mobileNumber = "";
        }
        this.mobileNumber = mobileNumber;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        if(address == null) {
            address = new Address();
        }
        this.address = address;
    }

    public String getPreferredName() {
        return preferredName;
    }

    public void setPreferredName(String preferredName) {
        if(preferredName == null) {
            preferredName = "";
        }
        this.preferredName = preferredName;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        if(profilePhoto == null) {
            profilePhoto = "";
        }
        this.profilePhoto = profilePhoto;
    }
}

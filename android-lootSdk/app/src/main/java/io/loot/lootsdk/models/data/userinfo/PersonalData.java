package io.loot.lootsdk.models.data.userinfo;


import java.io.Serializable;

public class PersonalData implements Serializable {

    private String firstName;
    private String lastName;
    private String preferredFistName;
    private String birthDate;
    private String preferredLastName;
    private String title;
    private String gender;
    private OnBoardingAddress address;
    private String profilePhotoUrl;

    public PersonalData() {
        this.firstName = "";
        this.lastName = "";
        this.preferredFistName = "";
        this.birthDate = "";
        this.preferredLastName = "";
        this.title = "";
        this.gender = "";
        this.address = new OnBoardingAddress();
        this.profilePhotoUrl = "";
    }

    public PersonalData(PersonalData personalData) {
        this.firstName = personalData.getFirstName();
        this.lastName = personalData.getLastName();
        this.preferredFistName = personalData.getPreferredFistName();
        this.birthDate = personalData.getBirthDate();
        this.preferredLastName = personalData.getPreferredLastName();
        this.title = personalData.getTitle();
        this.gender = personalData.getGender();
        this.address = new OnBoardingAddress(personalData.getAddress());
        this.profilePhotoUrl = personalData.getProfilePhotoUrl();
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

    public String getPreferredFistName() {
        return preferredFistName;
    }

    public void setPreferredFistName(String preferredFistName) {
        if(preferredFistName == null) {
            preferredFistName = "";
        }
        this.preferredFistName = preferredFistName;
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

    public String getPreferredLastName() {
        return preferredLastName;
    }

    public void setPreferredLastName(String preferredLastName) {
        if(preferredLastName == null) {
            preferredLastName = "";
        }
        this.preferredLastName = preferredLastName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if(title == null) {
            title = "";
        }
        this.title = title;
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

    public OnBoardingAddress getAddress() {
        return address;
    }

    public void setAddress(OnBoardingAddress address) {
        if(address == null) {
            address = new OnBoardingAddress();
        }
        this.address = address;
    }

    public String getProfilePhotoUrl() {
        return profilePhotoUrl;
    }

    public void setProfilePhotoUrl(String profilePhotoUrl) {
        if(profilePhotoUrl == null) {
            profilePhotoUrl = "";
        }
        this.profilePhotoUrl = profilePhotoUrl;
    }
}

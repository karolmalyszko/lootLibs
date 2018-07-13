package io.loot.lootsdk.models.data.userinfo;

import java.io.Serializable;

public class UserAccountData implements Serializable {

    private String email;
    private String displayName;
    private String phoneNumber;
    private String photoUri;

    public UserAccountData() {
        this.email = "";
        this.displayName = "";
        this.phoneNumber = "";
        this.photoUri = "";
    }

    public UserAccountData(UserAccountData userAccountData) {
        this.email = userAccountData.getEmail();
        this.displayName = userAccountData.getDisplayName();
        this.phoneNumber = userAccountData.getPhoneNumber();
        this.photoUri = userAccountData.getPhotoUri();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null) {
            email = "";
        }
        this.email = email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        if (displayName == null) {
            displayName = "";
        }
        this.displayName = displayName;
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

    public String getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(String photoUri) {
        if (photoUri == null) {
            photoUri = "";
        }
        this.photoUri = photoUri;
    }
}

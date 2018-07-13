package io.loot.lootsdk.models.data.userinfo;

import java.io.Serializable;

public class UserInfo implements Serializable {

    private String email;
    private String countryCode;
    private PersonalDetails personalDetails;
    private boolean legend;

    public UserInfo() {
        this.email = "";
        this.countryCode = "";
        this.personalDetails = new PersonalDetails();
        this.legend = false;
    }

    public UserInfo(UserInfo userInfo) {
        this.email = userInfo.getEmail();
        this.countryCode = userInfo.getCountryCode();
        this.personalDetails = new PersonalDetails(userInfo.getPersonalDetails());
        this.legend = userInfo.isLegend();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if(email == null) {
            email = "";
        }
        this.email = email;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        if(countryCode == null) {
            countryCode = "";
        }
        this.countryCode = countryCode;
    }

    public PersonalDetails getPersonalDetails() {
        return personalDetails;
    }

    public void setPersonalDetails(PersonalDetails personalDetails) {
        if(personalDetails == null) {
            personalDetails = new PersonalDetails();
        }
        this.personalDetails = personalDetails;
    }

    public boolean isLegend() {
        return legend;
    }

    public void setLegend(boolean legend) {
        this.legend = legend;
    }
}

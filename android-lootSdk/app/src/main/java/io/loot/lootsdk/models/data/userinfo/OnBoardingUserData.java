package io.loot.lootsdk.models.data.userinfo;


import java.io.Serializable;

public class OnBoardingUserData implements Serializable {

    private String email;
    private String phoneNumber;
    private String lastFinishedStep;
    private PersonalData personalData;
    private String token;
    private Kyc kyc;
    private String intercomHash;
    private String publicId;

    public OnBoardingUserData() {
          email = "";
          phoneNumber = "";
          lastFinishedStep = "";
          personalData = new PersonalData();
          token = "";
          kyc = new Kyc();
          intercomHash = "";
          publicId = "";

    }

    public OnBoardingUserData(OnBoardingUserData onBoardingUserData) {
        email = onBoardingUserData.getEmail();
        phoneNumber = onBoardingUserData.getPhoneNumber();
        lastFinishedStep = onBoardingUserData.getLastFinishedStep();
        personalData = new PersonalData(onBoardingUserData.getPersonalData());
        token = onBoardingUserData.getToken();
        kyc = new Kyc(onBoardingUserData.getKyc());
        intercomHash = onBoardingUserData.getIntercomHash();
        publicId = onBoardingUserData.getPublicId();

    }

    public String getIntercomHash() {
        return intercomHash;
    }

    public void setIntercomHash(String intercomHash) {
        if(intercomHash == null) {
            intercomHash = "";
        }
        this.intercomHash = intercomHash;
    }

    public String getPublicId() {
        return publicId;
    }

    public void setPublicId(String publicId) {
        if(publicId == null) {
            publicId = "";
        }
        this.publicId = publicId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        if(token == null) {
            token = "";
        }
        this.token = token;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        if(phoneNumber == null) {
            phoneNumber = "";
        }
        this.phoneNumber = phoneNumber;
    }

    public String getLastFinishedStep() {
        return lastFinishedStep;
    }

    public void setLastFinishedStep(String lastFinishedStep) {
        if(lastFinishedStep == null) {
            lastFinishedStep = "";
        }
        this.lastFinishedStep = lastFinishedStep;
    }

    public PersonalData getPersonalData() {
        return personalData;
    }

    public void setPersonalData(PersonalData personalData) {
        if(personalData == null) {
            personalData = new PersonalData();
        }
        this.personalData = personalData;
    }

    public Kyc getKyc() {
        return kyc;
    }

    public void setKyc(Kyc kyc) {
        if(kyc == null) {
            kyc = new Kyc();
        }
        this.kyc = kyc;
    }

    @Override
    public String toString() {
        return "OnBoardingUserData{" +
                "email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", lastFinishedStep='" + lastFinishedStep + '\'' +
                ", personalData=" + personalData +
                ", token='" + token + '\'' +
                ", kyc=" + kyc +
                ", intercomHash='" + intercomHash + '\'' +
                ", publicId='" + publicId + '\'' +
                '}';
    }
}

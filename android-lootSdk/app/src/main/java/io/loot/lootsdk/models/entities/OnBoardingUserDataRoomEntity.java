package io.loot.lootsdk.models.entities;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import io.loot.lootsdk.models.data.userinfo.OnBoardingUserData;
import io.loot.lootsdk.models.orm.KycEntity;
import io.loot.lootsdk.models.orm.PersonalDataEntity;

@android.arch.persistence.room.Entity(tableName = "OnboardingUserData")
public class OnBoardingUserDataRoomEntity {

    private String email;
    private String phoneNumber;
    private String lastFinishedStep;
    private String token;
    private String intercomHash;

    @PrimaryKey
    @NonNull
    private String publicId = "0";

    @Embedded(prefix = "personal_data_")
    private PersonalDataEntity personalData;
    @Embedded(prefix = "kyc_")
    private KycEntity kyc;

    public PersonalDataEntity getPersonalData() {
        if (personalData == null) {
            personalData = new PersonalDataEntity();
        }

        return personalData;
    }

    public void setPersonalData(PersonalDataEntity personalData) {
        this.personalData = personalData;
    }

    public KycEntity getKyc() {
        if (kyc == null) {
            kyc = new KycEntity();
        }

        return kyc;
    }

    public void setKyc(KycEntity kyc) {
        this.kyc = kyc;
    }

    public String getEmail() {
        if (email == null) {
            email = "";
        }

        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        if (phoneNumber == null) {
            phoneNumber = "";
        }

        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLastFinishedStep() {
        if (lastFinishedStep == null) {
            lastFinishedStep = "";
        }

        return this.lastFinishedStep;
    }

    public void setLastFinishedStep(String lastFinishedStep) {
        this.lastFinishedStep = lastFinishedStep;
    }

    public String getToken() {
        if (token == null) {
            token = "";
        }

        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getIntercomHash() {
        if (intercomHash == null) {
            intercomHash = "";
        }

        return this.intercomHash;
    }

    public void setIntercomHash(String intercomHash) {
        this.intercomHash = intercomHash;
    }

    @NonNull
    public String getPublicId() {
        if (publicId == null) {
            publicId = "";
        }

        return this.publicId;
    }

    public void setPublicId(@NonNull String publicId) {
        this.publicId = publicId;
    }


    public OnBoardingUserData parseToDataObject() {
        OnBoardingUserData userData = new OnBoardingUserData();
        userData.setEmail(getEmail());
        userData.setIntercomHash(getIntercomHash());
        userData.setKyc(getKyc().parseToDataObject());
        userData.setLastFinishedStep(getLastFinishedStep());
        userData.setPersonalData(getPersonalData().parseToDataObject());
        userData.setPhoneNumber(getPhoneNumber());
        userData.setPublicId(getPublicId());
        userData.setToken(getToken());

        return userData;
    }

    public static OnBoardingUserDataRoomEntity parseToEntity(OnBoardingUserData userData) {
        OnBoardingUserDataRoomEntity entity = new OnBoardingUserDataRoomEntity();

        entity.setEmail(userData.getEmail());
        entity.setLastFinishedStep(userData.getLastFinishedStep());
        entity.setPhoneNumber(userData.getPhoneNumber());
        entity.setToken(userData.getToken());
        entity.setIntercomHash(userData.getIntercomHash());
        entity.setPublicId(userData.getPublicId());
        entity.setKyc(KycEntity.parseToEntity(userData.getKyc()));
        entity.setPersonalData(PersonalDataEntity.parseToEntity(userData.getPersonalData()));

        return entity;
    }

    @Override
    public String toString() {
        return "OnBoardingUserDataRoomEntity{" +
                "email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", lastFinishedStep='" + lastFinishedStep + '\'' +
                ", token='" + token + '\'' +
                ", intercomHash='" + intercomHash + '\'' +
                ", publicId='" + publicId + '\'' +
                ", personalData=" + personalData +
                ", kyc=" + kyc +
                '}';
    }
}

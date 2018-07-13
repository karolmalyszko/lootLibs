package io.loot.lootsdk.models.orm;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;


import io.loot.lootsdk.models.data.userinfo.PersonalData;

@Entity
public class PersonalDataEntity {

    @PrimaryKey
    private long entityId;
    private String firstName;
    private String lastName;
    private String preferredFistName;
    private String birthDate;
    private String preferredLastName;
    private String title;
    private String gender;

    @Embedded(prefix = "address_")
    private OnBoardingAddressEntity address;

    private String profilePhotoUrl;

    public PersonalData parseToDataObject() {
        PersonalData personalData = new PersonalData();
        personalData.setFirstName(getFirstName());
        personalData.setLastName(getLastName());
        personalData.setPreferredFistName(getPreferredFistName());
        personalData.setBirthDate(getBirthDate());
        personalData.setPreferredLastName(getPreferredLastName());
        personalData.setTitle(getTitle());
        personalData.setGender(getGender());
        personalData.setAddress(getAddress().parseToDataObject());
        personalData.setProfilePhotoUrl(getProfilePhotoUrl());
        return personalData;
    }

    public static PersonalDataEntity parseToEntity(PersonalData personalData) {
        PersonalDataEntity entity = new PersonalDataEntity();

        entity.setTitle(personalData.getTitle());
        entity.setAddress(OnBoardingAddressEntity.parseToEntity(personalData.getAddress()));
        entity.setBirthDate(personalData.getBirthDate());
        entity.setFirstName(personalData.getFirstName());
        entity.setLastName(personalData.getLastName());
        entity.setPreferredFistName(personalData.getPreferredFistName());
        entity.setPreferredLastName(personalData.getPreferredLastName());
        entity.setProfilePhotoUrl(personalData.getProfilePhotoUrl());
        entity.setGender(personalData.getGender());

        return entity;
    }

    public String getFirstName() {
        if (firstName == null) {
            firstName = "";
        }

        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        if (lastName == null) {
            lastName = "";
        }

        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPreferredFistName() {
        if (preferredFistName == null) {
            preferredFistName = "";
        }

        return this.preferredFistName;
    }

    public void setPreferredFistName(String preferredFistName) {
        this.preferredFistName = preferredFistName;
    }

    public String getBirthDate() {
        if (birthDate == null) {
            birthDate = "";
        }

        return this.birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getPreferredLastName() {
        if (preferredLastName == null) {
            preferredLastName = "";
        }

        return this.preferredLastName;
    }

    public void setPreferredLastName(String preferredLastName) {
        this.preferredLastName = preferredLastName;
    }

    public String getTitle() {
        if (title == null) {
            title = "";
        }

        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGender() {
        if (gender == null) {
            gender = "";
        }

        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProfilePhotoUrl() {
        if (profilePhotoUrl == null) {
            profilePhotoUrl = "";
        }

        return this.profilePhotoUrl;
    }

    public void setProfilePhotoUrl(String profilePhotoUrl) {
        this.profilePhotoUrl = profilePhotoUrl;
    }

    public OnBoardingAddressEntity getAddress() {
        if (address == null) {
            address = new OnBoardingAddressEntity();
        }

        return address;
    }

    public void setAddress(OnBoardingAddressEntity address) {
        this.address = address;
    }

    public long getEntityId() {
        return entityId;
    }

    public void setEntityId(long entityId) {
        this.entityId = entityId;
    }
}

package io.loot.lootsdk.models.orm;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Relation;

import io.loot.lootsdk.models.data.userinfo.PersonalDetails;

//TODO Add resolving Entities
@android.arch.persistence.room.Entity(tableName = "PersonalDetails")
public class PersonalDetailsEntity {

    @PrimaryKey
    private long entityId = 0;
    private String titleCode;
    private String firstName;
    private String lastName;
    private String middleName;
    private String birthDate;
    private String gender;
    private String mobileNumber;

    @Embedded(prefix = "address_")
    private AddressEntity address;

    private String preferredName;
    private String profilePhoto;

    public PersonalDetails parseToDataObject() {
        PersonalDetails personalDetails = new PersonalDetails();
        personalDetails.setTitleCode(titleCode);
        personalDetails.setFirstName(firstName);
        personalDetails.setLastName(lastName);
        personalDetails.setMiddleName(middleName);
        personalDetails.setBirthDate(birthDate);
        personalDetails.setGender(gender);
        personalDetails.setMobileNumber(mobileNumber);

        if (getAddress() != null) {
            personalDetails.setAddress(getAddress().parseToDataObject());
        }

        personalDetails.setPreferredName(preferredName);
        personalDetails.setProfilePhoto(profilePhoto);
        return personalDetails;
    }

    public long getEntityId() {
        return this.entityId;
    }

    public void setEntityId(long entityId) {
        this.entityId = entityId;
    }

    public String getTitleCode() {
        return this.titleCode;
    }

    public void setTitleCode(String titleCode) {
        this.titleCode = titleCode;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return this.middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getBirthDate() {
        return this.birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMobileNumber() {
        return this.mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getPreferredName() {
        return this.preferredName;
    }

    public void setPreferredName(String preferredName) {
        this.preferredName = preferredName;
    }

    public String getProfilePhoto() {
        return this.profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public AddressEntity getAddress() {
        return address;
    }

    public void setAddress(AddressEntity address) {
        this.address = address;
    }

}

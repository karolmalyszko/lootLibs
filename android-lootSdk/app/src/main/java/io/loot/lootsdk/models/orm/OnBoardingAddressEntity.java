package io.loot.lootsdk.models.orm;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import io.loot.lootsdk.models.data.userinfo.OnBoardingAddress;

@Entity
public class OnBoardingAddressEntity {

    @PrimaryKey
    private long entityId;
    private String summaryLine;
    private String premise;
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String postTown;
    private String county;
    private String country;
    private String postCode;

    public OnBoardingAddress parseToDataObject() {
        OnBoardingAddress onBoardingAddress = new OnBoardingAddress();

        onBoardingAddress.setAddressLine1(addressLine1);
        onBoardingAddress.setAddressLine2(addressLine2);
        onBoardingAddress.setAddressLine3(addressLine3);
        onBoardingAddress.setCountry(country);
        onBoardingAddress.setCounty(county);
        onBoardingAddress.setPostCode(postCode);
        onBoardingAddress.setPostTown(postTown);
        onBoardingAddress.setPremise(premise);
        onBoardingAddress.setSummaryLine(summaryLine);

        return onBoardingAddress;
    }

    public static OnBoardingAddressEntity parseToEntity(OnBoardingAddress address) {
        OnBoardingAddressEntity entity = new OnBoardingAddressEntity();

        entity.setAddressLine1(address.getAddressLine1());
        entity.setAddressLine2(address.getAddressLine2());
        entity.setAddressLine3(address.getAddressLine3());
        entity.setCountry(address.getCountry());
        entity.setCounty(address.getCounty());
        entity.setPostCode(address.getPostCode());
        entity.setPostTown(address.getPostTown());
        entity.setPremise(address.getPremise());
        entity.setSummaryLine(address.getSummaryLine());

        return entity;
    }

    public String getSummaryLine() {
        return this.summaryLine;
    }

    public void setSummaryLine(String summaryLine) {
        this.summaryLine = summaryLine;
    }

    public String getPremise() {
        return this.premise;
    }

    public void setPremise(String premise) {
        this.premise = premise;
    }

    public String getAddressLine1() {
        return this.addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return this.addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getAddressLine3() {
        return this.addressLine3;
    }

    public void setAddressLine3(String addressLine3) {
        this.addressLine3 = addressLine3;
    }

    public String getPostTown() {
        return this.postTown;
    }

    public void setPostTown(String postTown) {
        this.postTown = postTown;
    }

    public String getCounty() {
        return this.county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostCode() {
        return this.postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public long getEntityId() {
        return entityId;
    }

    public void setEntityId(long entityId) {
        this.entityId = entityId;
    }

}

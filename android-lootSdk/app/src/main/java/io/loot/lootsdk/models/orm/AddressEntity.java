package io.loot.lootsdk.models.orm;

import android.arch.persistence.room.PrimaryKey;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import io.loot.lootsdk.models.data.userinfo.Address;

@android.arch.persistence.room.Entity(tableName = "Address")
public class AddressEntity {

    @PrimaryKey
    private long entityId = 0;

    private String postTown;
    private String postcode;
    private String county;
    private String country;
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String summaryLine;
    private String premise;
    private String createdAt;

    public Address parseToDataObject() {
        Address address = new Address();

        address.setPostTown(postTown);
        address.setPostcode(postcode);
        address.setAddressLine1(addressLine1);
        address.setAddressLine2(addressLine2);
        address.setAddressLine3(addressLine3);
        address.setSummaryLine(summaryLine);
        address.setPremise(premise);
        address.setCreatedAt(createdAt);
        address.setCountry(country);
        address.setCounty(county);

        return address;
    }

    public long getEntityId() {
        return this.entityId;
    }

    public void setEntityId(long entityId) {
        this.entityId = entityId;
    }

    public String getPostTown() {
        return this.postTown;
    }

    public void setPostTown(String postTown) {
        this.postTown = postTown;
    }

    public String getPostcode() {
        return this.postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
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

    public String getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}

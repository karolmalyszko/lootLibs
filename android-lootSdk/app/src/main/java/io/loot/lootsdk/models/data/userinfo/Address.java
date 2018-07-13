package io.loot.lootsdk.models.data.userinfo;

import java.io.Serializable;

public class Address implements Serializable {
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

    public Address() {
        this.postTown = "";
        this.postcode = "";
        this.county = "";
        this.country = "";
        this.addressLine1 = "";
        this.addressLine2 = "";
        this.addressLine3 = "";
        this.summaryLine = "";
        this.premise = "";
        this.createdAt = "";
    }

    public Address(Address address) {
        this.postTown = address.getPostTown();
        this.postcode = address.getPostcode();
        this.county = address.getCounty();
        this.country = address.getCountry();
        this.addressLine1 = address.getAddressLine1();
        this.addressLine2 = address.getAddressLine2();
        this.addressLine3 = address.getAddressLine3();
        this.summaryLine = address.getSummaryLine();
        this.premise = address.getPremise();
        this.createdAt = address.getCreatedAt();
    }

    public String getPostTown() {
        return postTown;
    }

    public void setPostTown(String postTown) {
        if (postTown == null) {
            postTown = "";
        }
        this.postTown = postTown;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        if (postcode == null) {
            postcode = "";
        }
        this.postcode = postcode;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        if (county == null) {
            county = "";
        }
        this.county = county;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        if (country == null) {
            country = "";
        }
        this.country = country;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        if (addressLine1 == null) {
            addressLine1 = "";
        }
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        if (addressLine2 == null) {
            addressLine2 = "";
        }
        this.addressLine2 = addressLine2;
    }

    public String getAddressLine3() {
        return addressLine3;
    }

    public void setAddressLine3(String addressLine3) {
        if (addressLine3 == null) {
            addressLine3 = "";
        }
        this.addressLine3 = addressLine3;
    }

    public String getSummaryLine() {
        return summaryLine;
    }

    public void setSummaryLine(String summaryLine) {
        if (summaryLine == null) {
            summaryLine = "";
        }
        this.summaryLine = summaryLine;
    }

    public String getPremise() {
        return premise;
    }

    public void setPremise(String premise) {
        if (premise == null) {
            premise = "";
        }
        this.premise = premise;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        if (createdAt == null) {
            createdAt = "";
        }
        this.createdAt = createdAt;
    }

    public String toString() {
        String addressLine1 = this.addressLine1 != null && !this.addressLine1.isEmpty() ? this.addressLine1 : "";
        String addressLine2 = this.addressLine2 != null && !this.addressLine2.isEmpty() ? "\n"+this.addressLine2 : "";
        String addressLine3 = this.addressLine3 != null && !this.addressLine3.isEmpty() ? "\n"+this.addressLine3 : "";
        String postTown = this.postTown != null && !this.postTown.isEmpty() ? "\n"+this.postTown : "";
        String postCode = this.postcode != null && !this.postcode.isEmpty() ? "\n"+this.postcode : "";
        String county = this.county != null && !this.county.isEmpty() ? "\n"+this.county : "";
        String country = this.country != null && !this.country.isEmpty() ? "\n"+this.country : "";

        return addressLine1 + addressLine2 + addressLine3 + postTown + postCode + county + country;
    }

}

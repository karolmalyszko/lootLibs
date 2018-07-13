package io.loot.lootsdk.models.data.userinfo;

import java.io.Serializable;


public class OnBoardingAddress implements Serializable {

    private String summaryLine;
    private String premise;
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String postTown;
    private String county;
    private String country;
    private String postCode;

    public OnBoardingAddress() {
        this.summaryLine = "";
        this.premise = "";
        this.addressLine1 = "";
        this.addressLine2 = "";
        this.addressLine3 = "";
        this.postTown = "";
        this.county = "";
        this.country = "";
        this.postCode = "";
    }

    public OnBoardingAddress(OnBoardingAddress onBoardingAddress) {
        this.summaryLine = onBoardingAddress.getSummaryLine();
        this.premise = onBoardingAddress.getPremise();
        this.addressLine1 = onBoardingAddress.getAddressLine1();
        this.addressLine2 = onBoardingAddress.getAddressLine2();
        this.addressLine3 = onBoardingAddress.getAddressLine3();
        this.postTown = onBoardingAddress.getPostTown();
        this.county = onBoardingAddress.getCounty();
        this.country = onBoardingAddress.getCountry();
        this.postCode = onBoardingAddress.getPostCode();
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

    public String getPostTown() {
        return postTown;
    }

    public void setPostTown(String postTown) {
        if (postTown == null) {
            postTown = "";
        }
        this.postTown = postTown;
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

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        if (postCode == null) {
            postCode = "";
        }
        this.postCode = postCode;
    }

    @Override
    public boolean equals(Object obj) {
        return this.toString().equals(obj.toString());
    }

    @Override
    public String toString() {
        return summaryLine+" "+premise+" "+addressLine1+" "+addressLine2+" "+addressLine3+" "+postTown+" "+county+" "+country+" "+postCode;
    }
}

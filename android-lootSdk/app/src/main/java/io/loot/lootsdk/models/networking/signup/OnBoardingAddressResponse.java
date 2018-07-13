package io.loot.lootsdk.models.networking.signup;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.loot.lootsdk.models.data.userinfo.OnBoardingAddress;
import lombok.Data;

public @Data class OnBoardingAddressResponse implements Serializable {

    @SerializedName("summary_line")
    private String summaryLine;
    @SerializedName("premise")
    private String premise;
    @SerializedName("address_line_1")
    private String addressLine1;
    @SerializedName("address_line_2")
    private String addressLine2;
    @SerializedName("address_line_3")
    private String addressLine3;
    @SerializedName("post_town")
    private String postTown;
    @SerializedName("county")
    private String county;
    @SerializedName("country")
    private String country;
    @SerializedName("postcode")
    private String postCode;

    public static OnBoardingAddress parseToDataObject(OnBoardingAddressResponse onBoardingAddressResponse) {
        if (onBoardingAddressResponse == null) {
            onBoardingAddressResponse = new OnBoardingAddressResponse();
        }
        OnBoardingAddress address = new OnBoardingAddress();
        address.setSummaryLine(onBoardingAddressResponse.getSummaryLine());
        address.setPremise(onBoardingAddressResponse.getPremise());
        address.setAddressLine1(onBoardingAddressResponse.getAddressLine1());
        address.setAddressLine2(onBoardingAddressResponse.getAddressLine2());
        address.setAddressLine3(onBoardingAddressResponse.getAddressLine3());
        address.setPostTown(onBoardingAddressResponse.getPostTown());
        address.setCounty(onBoardingAddressResponse.getCounty());
        address.setCountry(onBoardingAddressResponse.getCountry());
        address.setPostCode(onBoardingAddressResponse.getPostCode());

        return address;
    }

    public static OnBoardingAddressResponse parseToResponseObject(OnBoardingAddress onBoardingAddress) {
        if (onBoardingAddress == null) {
            onBoardingAddress = new OnBoardingAddress();
        }
        OnBoardingAddressResponse addressResponse = new OnBoardingAddressResponse();
        addressResponse.setPostTown(onBoardingAddress.getPostTown());
        addressResponse.setAddressLine1(onBoardingAddress.getAddressLine1());
        addressResponse.setPostCode(onBoardingAddress.getPostCode());
        addressResponse.setAddressLine2(onBoardingAddress.getAddressLine2());
        addressResponse.setAddressLine3(onBoardingAddress.getAddressLine3());
        addressResponse.setSummaryLine(onBoardingAddress.getSummaryLine());
        addressResponse.setPremise(onBoardingAddress.getPremise());
        addressResponse.setCountry(onBoardingAddress.getCountry());
        addressResponse.setCounty(onBoardingAddress.getCounty());

        return addressResponse;
    }


}

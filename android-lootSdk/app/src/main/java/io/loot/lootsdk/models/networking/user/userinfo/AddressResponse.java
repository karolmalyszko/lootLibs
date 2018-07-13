package io.loot.lootsdk.models.networking.user.userinfo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.loot.lootsdk.models.data.userinfo.Address;
import io.loot.lootsdk.models.data.userinfo.OnBoardingAddress;
import io.loot.lootsdk.models.orm.AddressEntity;
import lombok.Data;

public @Data class AddressResponse implements Serializable {

    @SerializedName("post_town")
    private String postTown;

    @SerializedName("postcode")
    private String postcode;

    @SerializedName("county")
    private String county;

    @SerializedName("country")
    private String country;

    @SerializedName("address_line_1")
    private String addressLine1;

    @SerializedName("address_line_2")
    private String addressLine2;

    @SerializedName("address_line_3")
    private String addressLine3;

    @SerializedName("summary_line")
    private String summaryLine;

    @SerializedName("premise")
    private String premise;

    @SerializedName("created_at")
    private String createdAt;


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

    public static Address parseToDataObject(AddressResponse addressResponse) {
        if (addressResponse == null) {
            addressResponse = new AddressResponse();
        }
        Address address = new Address();

        address.setPostTown(addressResponse.getPostTown());
        address.setPostcode(addressResponse.getPostcode());
        address.setAddressLine1(addressResponse.getAddressLine1());
        address.setAddressLine2(addressResponse.getAddressLine2());
        address.setAddressLine3(addressResponse.getAddressLine3());
        address.setSummaryLine(addressResponse.getSummaryLine());
        address.setPremise(addressResponse.getPremise());
        address.setCreatedAt(addressResponse.getCreatedAt());
        address.setCountry(addressResponse.getCountry());
        address.setCounty(addressResponse.getCounty());

        return address;
    }

    public static AddressEntity parseToEntityObject(AddressResponse addressResponse) {
        if (addressResponse == null) {
            addressResponse = new AddressResponse();
        }
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setPostTown(addressResponse.getPostTown());
        addressEntity.setPostcode(addressResponse.getPostcode());
        addressEntity.setAddressLine1(addressResponse.getAddressLine1());
        addressEntity.setAddressLine2(addressResponse.getAddressLine2());
        addressEntity.setAddressLine3(addressResponse.getAddressLine3());
        addressEntity.setSummaryLine(addressResponse.getSummaryLine());
        addressEntity.setPremise(addressResponse.getPremise());
        addressEntity.setCreatedAt(addressResponse.getCreatedAt());
        addressEntity.setCountry(addressResponse.getCountry());
        addressEntity.setCounty(addressResponse.getCounty());
        return addressEntity;
    }

    public static AddressResponse parseToResponseObject(Address address) {
        if (address == null) {
            address = new Address();
        }
        AddressResponse addressResponse = new AddressResponse();
        addressResponse.setPostTown(address.getPostTown());
        addressResponse.setPostcode(address.getPostcode());
        addressResponse.setAddressLine1(address.getAddressLine1());
        addressResponse.setAddressLine2(address.getAddressLine2());
        addressResponse.setAddressLine3(address.getAddressLine3());
        addressResponse.setSummaryLine(address.getSummaryLine());
        addressResponse.setPremise(address.getPremise());
        addressResponse.setCreatedAt(address.getCreatedAt());
        addressResponse.setCountry(address.getCountry());
        addressResponse.setCounty(address.getCounty());
        return addressResponse;
    }

}
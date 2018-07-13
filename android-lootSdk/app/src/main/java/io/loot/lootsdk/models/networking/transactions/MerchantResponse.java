package io.loot.lootsdk.models.networking.transactions;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.loot.lootsdk.models.data.transactions.Merchant;
import io.loot.lootsdk.models.orm.MerchantEntity;
import lombok.Data;

@Data
public class MerchantResponse implements Serializable {

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("brand_colour")
    private String brandColourHex;

    @SerializedName("description")
    private String description;

    @SerializedName("logo_url")
    private String logoUrl;

    @SerializedName("twitter_handle")
    private String twitter;

    public static Merchant parseToDataObject(MerchantResponse merchantResponse) {
        if (merchantResponse == null) {
            merchantResponse = new MerchantResponse();
        }
        Merchant merchant = new Merchant();
        merchant.setId(merchantResponse.getId());
        merchant.setName(merchantResponse.getName());
        merchant.setBrandColourHex(merchantResponse.getBrandColourHex());
        merchant.setDescription(merchantResponse.getDescription());
        merchant.setLogoUrl(merchantResponse.getLogoUrl());
        merchant.setTwitter(merchantResponse.getTwitter());
        return merchant;
    }

    public static MerchantEntity parseToEntityObject(MerchantResponse merchantResponse) {
        if (merchantResponse == null) {
            merchantResponse = new MerchantResponse();
        }
        MerchantEntity merchantEntity = new MerchantEntity();
        merchantEntity.setId(merchantResponse.getId());
        merchantEntity.setName(merchantResponse.getName());
        merchantEntity.setBrandColourHex(merchantResponse.getBrandColourHex());
        merchantEntity.setDescription(merchantResponse.getDescription());
        merchantEntity.setLogoUrl(merchantResponse.getLogoUrl());
        merchantEntity.setTwitter(merchantResponse.getTwitter());
        return merchantEntity;
    }
}

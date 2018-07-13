package io.loot.lootsdk.models.orm;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import io.loot.lootsdk.models.data.transactions.Merchant;

@Entity(tableName = "Merchant")
public class MerchantEntity {

    @PrimaryKey
    @NonNull
    private String id = "0";
    private String name;
    private String brandColourHex;
    private String description;
    private String logoUrl;
    private String twitter;

    public Merchant parseToDataObject() {
        Merchant merchant = new Merchant();
        merchant.setId(id);
        merchant.setName(name);
        merchant.setBrandColourHex(brandColourHex);
        merchant.setDescription(description);
        merchant.setLogoUrl(logoUrl);
        merchant.setTwitter(twitter);
        return merchant;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrandColourHex() {
        return this.brandColourHex;
    }

    public void setBrandColourHex(String brandColourHex) {
        this.brandColourHex = brandColourHex;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLogoUrl() {
        return this.logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getTwitter() {
        return this.twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }
}

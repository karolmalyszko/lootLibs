package io.loot.lootsdk.models.data.transactions;


import java.io.Serializable;

public class Merchant implements Serializable {

    private String id;
    private String name;
    private String brandColourHex;
    private String description;
    private String logoUrl;
    private String twitter;

    public Merchant() {
        id = "";
        name = "";
        brandColourHex = "";
        description = "";
        logoUrl = "";
        twitter = "";
    }

    public Merchant(Merchant merchant) {
        id = merchant.getId();
        name = merchant.getName();
        brandColourHex = merchant.getBrandColourHex();
        description = merchant.getDescription();
        logoUrl = merchant.getLogoUrl();
        twitter = merchant.getTwitter();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (id == null) {
            id = "";
        }
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null) {
            name = "";
        }
        this.name = name;
    }

    public String getBrandColourHex() {
        return brandColourHex;
    }

    public void setBrandColourHex(String brandColourHex) {
        if (brandColourHex == null) {
            brandColourHex = "";
        }
        this.brandColourHex = brandColourHex;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description == null) {
            description = "";
        }
        this.description = description;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        if (logoUrl == null) {
            logoUrl = "";
        }
        this.logoUrl = logoUrl;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        if (twitter == null) {
            twitter = "";
        }
        this.twitter = twitter;
    }
}

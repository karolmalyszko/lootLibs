package io.loot.lootsdk.models;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import io.loot.lootsdk.models.data.transactions.Merchant;
import io.loot.lootsdk.models.networking.transactions.MerchantResponse;
import io.loot.lootsdk.models.orm.MerchantEntity;

public class MerchantTests {

    private MerchantResponse response;

    @Before
    public void setUp() {
        String id = "id";
        String name = "name";
        String brandColourHex = "brandColour";
        String description = "description";
        String logoUrl = "logourl";
        String twitter = "twitter";

        response = new MerchantResponse();
        response.setId(id);
        response.setName(name);
        response.setBrandColourHex(brandColourHex);
        response.setDescription(description);
        response.setLogoUrl(logoUrl);
        response.setTwitter(twitter);
    }

    @Test
    public void merchantResponseToData() throws Exception {
        Merchant data = MerchantResponse.parseToDataObject(response);

        Assert.assertEquals(response.getId(), data.getId());
        Assert.assertEquals(response.getName(), data.getName());
        Assert.assertEquals(response.getBrandColourHex(), data.getBrandColourHex());
        Assert.assertEquals(response.getDescription(), data.getDescription());
        Assert.assertEquals(response.getLogoUrl(), data.getLogoUrl());
        Assert.assertEquals(response.getTwitter(), data.getTwitter());
    }

    @Test
    public void merchantResponseToEntity() throws Exception {
        MerchantEntity entity = MerchantResponse.parseToEntityObject(response);

        Assert.assertEquals(response.getId(), entity.getId());
        Assert.assertEquals(response.getName(), entity.getName());
        Assert.assertEquals(response.getBrandColourHex(), entity.getBrandColourHex());
        Assert.assertEquals(response.getDescription(), entity.getDescription());
        Assert.assertEquals(response.getLogoUrl(), entity.getLogoUrl());
        Assert.assertEquals(response.getTwitter(), entity.getTwitter());
    }

    @Test
    public void merchantEntityToData() throws Exception {
        MerchantEntity entity = MerchantResponse.parseToEntityObject(response);
        Merchant data = entity.parseToDataObject();

        Assert.assertEquals(data.getId(), entity.getId());
        Assert.assertEquals(data.getName(), entity.getName());
        Assert.assertEquals(data.getBrandColourHex(), entity.getBrandColourHex());
        Assert.assertEquals(data.getDescription(), entity.getDescription());
        Assert.assertEquals(data.getLogoUrl(), entity.getLogoUrl());
        Assert.assertEquals(data.getTwitter(), entity.getTwitter());
    }


}

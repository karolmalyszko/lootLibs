package io.loot.lootsdk.models;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import io.loot.lootsdk.models.data.topUp.TopUpResult;
import io.loot.lootsdk.models.networking.topUp.TopUpResponse;

public class TopUpResultTests {

    private TopUpResponse response;

    @Before
    public void setUp() {
        response = new TopUpResponse();
        response.setRedirectUrl("redirectUrl");
        response.setRequired3ds(true);
    }

    @Test
    public void topUpResultResponseToData() throws Exception {
        TopUpResult data = TopUpResponse.parseToDataObject(response);

        Assert.assertEquals(data.getRedirectUrl(), response.getRedirectUrl());
        Assert.assertEquals(data.isRequired3ds(), response.isRequired3ds());
    }

    @Test
    public void topUpResultShouldntHaveNulls() throws Exception {
        TopUpResult data = new TopUpResult();

        Assert.assertNotNull(data.getRedirectUrl());
        Assert.assertNotNull(data.isRequired3ds());
    }

    @Test
    public void topUpResultShouldntHaveNullsAfterSettingIt() throws Exception {
        TopUpResult data = new TopUpResult();
        data.setRedirectUrl(null);
        data.setRequired3ds(true);

        Assert.assertNotNull(data.getRedirectUrl());
        Assert.assertNotNull(data.isRequired3ds());
    }

}

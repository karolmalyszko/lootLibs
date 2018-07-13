package io.loot.lootsdk.models;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import io.loot.lootsdk.models.data.topUp.TopUpLimits;
import io.loot.lootsdk.models.data.userinfo.Address;
import io.loot.lootsdk.models.networking.topUp.TopUpLimitDetailsResponse;
import io.loot.lootsdk.models.networking.topUp.TopUpLimitsResponse;
import io.loot.lootsdk.models.networking.user.userinfo.AddressResponse;
import io.loot.lootsdk.models.orm.AddressEntity;

public class TopUpLimitsTests {

    private TopUpLimitsResponse response;

    @Before
    public void setUp() {
        TopUpLimitDetailsResponse limit = new TopUpLimitDetailsResponse();
        limit.setAmount("100");
        limit.setCharges(1);

        TopUpLimitDetailsResponse remaining = new TopUpLimitDetailsResponse();
        remaining.setAmount("6");
        remaining.setCharges(5);

        response = new TopUpLimitsResponse();
        response.setLimits(limit);
        response.setRemaining(remaining);
    }

    @Test
    public void dataObjectCantBeNullable() throws Exception {
        TopUpLimits data = TopUpLimitsResponse.parseToDataObject(response);

        Assert.assertNotNull(data);
    }

    @Test
    public void dataObjectCantBeNullableEvenWithNullsInside() throws Exception {
        response.setLimits(null);
        response.setRemaining(null);

        TopUpLimits data = TopUpLimitsResponse.parseToDataObject(response);

        Assert.assertNotNull(data);
    }

    @Test
    public void dataObjectIsParsedWithCorrectValues() throws Exception {
        TopUpLimits data = TopUpLimitsResponse.parseToDataObject(response);

        Assert.assertEquals(response.getLimits().getAmount(), data.getLimitAmount());
        Assert.assertEquals(response.getLimits().getCharges(), data.getLimitCharges());
        Assert.assertEquals(response.getRemaining().getAmount(), data.getRemainingAmount());
        Assert.assertEquals(response.getRemaining().getCharges(), data.getRemainingCharges());
    }

}

package io.loot.lootsdk.models;

import org.junit.Assert;
import org.junit.Test;

import io.loot.lootsdk.models.data.topUp.CardDetails;

public class CardDetailsTests {

    @Test
    public void cardDetailsShouldntHaveNullValues() throws Exception {
        CardDetails data = new CardDetails();

        Assert.assertNotNull(data.getCardNumber());
        Assert.assertNotNull(data.getCvv());
        Assert.assertNotNull(data.getExpiryMonth());
        Assert.assertNotNull(data.getExpiryYear());
    }

    @Test
    public void cardDetailsShouldntHaveNullValuesAfterSettingIt() throws Exception {
        CardDetails data = new CardDetails(null, 0, 0, null);

        Assert.assertNotNull(data.getCardNumber());
        Assert.assertNotNull(data.getCvv());
        Assert.assertNotNull(data.getExpiryMonth());
        Assert.assertNotNull(data.getExpiryYear());

    }

}

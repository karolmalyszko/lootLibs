package io.loot.lootsdk.models;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import io.loot.lootsdk.models.data.cards.CardActivation;
import io.loot.lootsdk.models.networking.cards.CardActivationResponse;
import io.loot.lootsdk.models.networking.cards.CardResponse;

public class CardActivationTest {

    private CardActivationResponse response;

    @Before
    public void setUp() {
        String id = "id";
        String accountId = "accountId";
        String status = "status";
        String pinCode = "pinCode";

        response = new CardActivationResponse();
        response.setStatus(status);
        response.setId(id);
        response.setAccountId(accountId);
        response.setPinCode(pinCode);
    }

    @Test
    public void cardActiviationResponseToData() throws Exception {
        CardActivation data = CardActivationResponse.parseToDataObject(response);

        Assert.assertEquals(data.getStatus(), response.getStatus());
        Assert.assertEquals(data.getId(), response.getId());
        Assert.assertEquals(data.getAccountId(), response.getAccountId());
        Assert.assertEquals(data.getPinCode(), response.getPinCode());
    }

    @Test
    public void cardActivationDataShouldntContainsNulls() throws Exception {
        CardActivation data = CardActivationResponse.parseToDataObject(response);

        Assert.assertNotEquals(data.getPinCode(), null);
        Assert.assertNotEquals(data.getAccountId(), null);
        Assert.assertNotEquals(data.getStatus(), null);
        Assert.assertNotEquals(data.getId(), null);
    }

    @Test
    public void cardActivationDataShouldntContainsNullsAfterParse() throws Exception {
        response.setAccountId(null);
        response.setStatus(null);
        response.setPinCode(null);
        response.setId(null);

        CardActivation data = CardActivationResponse.parseToDataObject(response);

        Assert.assertNotEquals(data.getPinCode(), null);
        Assert.assertNotEquals(data.getAccountId(), null);
        Assert.assertNotEquals(data.getStatus(), null);
        Assert.assertNotEquals(data.getId(), null);
    }

}

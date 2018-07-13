package io.loot.lootsdk.models;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import io.loot.lootsdk.models.data.cards.Card;
import io.loot.lootsdk.models.networking.cards.CardResponse;
import io.loot.lootsdk.models.orm.CardEntity;

public class CardTests {

    private CardResponse response;

    @Before
    public void setUp() {
        String createdAt = "";
        String design = "design";
        String id = "id";
        String status = "someStatus";

        response = new CardResponse();
        response.setCreatedAt(createdAt);
        response.setDesign(design);
        response.setId(id);
        response.setStatus(status);
    }

    @Test
    public void cardResponseToData() throws Exception {
        Card data = CardResponse.parseToDataObject(response);

        Assert.assertEquals(data.getCreatedAt(), response.getCreatedAt());
        Assert.assertEquals(data.getDesign(), response.getDesign());
        Assert.assertEquals(data.getId(), response.getId());
        Assert.assertEquals(data.getStatus(), response.getStatus());
    }

    @Test
    public void cardResponseToEntity() throws Exception {
        CardEntity entity = CardResponse.parseToEntityObject(response);

        Assert.assertEquals(entity.getCreatedAt(), response.getCreatedAt());
        Assert.assertEquals(entity.getDesign(), response.getDesign());
        Assert.assertEquals(entity.getId(), response.getId());
        Assert.assertEquals(entity.getStatus(), response.getStatus());
    }

    @Test
    public void cardEntityToData() throws Exception {
        CardEntity entity = CardResponse.parseToEntityObject(response);
        Card data = entity.parseToDataObject();

        Assert.assertEquals(entity.getCreatedAt(), data.getCreatedAt());
        Assert.assertEquals(entity.getDesign(), data.getDesign());
        Assert.assertEquals(entity.getId(), data.getId());
        Assert.assertEquals(entity.getStatus(), data.getStatus());
    }

    @Test
    public void cardDataShouldntContainsNulls() throws Exception {
        Card card = new Card();

        Assert.assertNotEquals(card.getCreatedAt(), null);
        Assert.assertNotEquals(card.getDesign(), null);
        Assert.assertNotEquals(card.getStatus(), null);
        Assert.assertNotEquals(card.getId(), null);
    }

    @Test
    public void cardDataShouldntContainsNullsAfterParse() throws Exception {
        response.setStatus(null);
        response.setId(null);
        response.setDesign(null);
        response.setCreatedAt(null);

        Card card = CardResponse.parseToDataObject(response);
        Assert.assertNotEquals(card.getCreatedAt(), null);
        Assert.assertNotEquals(card.getDesign(), null);
        Assert.assertNotEquals(card.getStatus(), null);
        Assert.assertNotEquals(card.getId(), null);

    }

}

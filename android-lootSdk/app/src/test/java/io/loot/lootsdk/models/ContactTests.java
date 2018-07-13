package io.loot.lootsdk.models;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import io.loot.lootsdk.models.data.contacts.Contact;
import io.loot.lootsdk.models.networking.contacts.ContactDetailsResponse;
import io.loot.lootsdk.models.orm.ContactEntity;
import io.loot.lootsdk.models.orm.SavedContactEntity;

public class ContactTests {
    private ContactDetailsResponse response;

    @Before
    public void setUp() {
        String id = "id";
        String name = "name";
        String accNum = "accNum";
        String sortCode = "sortCode";
        String profilePhoto = "sortCode";


        response = new ContactDetailsResponse();
        response.setId(id);
        response.setName(name);
        response.setAccountNumber(accNum);
        response.setSortCode(sortCode);
        response.setProfilePhoto(profilePhoto);
    }

    @Test
    public void contactDetailsResponseToData() throws Exception {
        Contact data = ContactDetailsResponse.parseToDataObject(response);

        Assert.assertEquals(data.getContactId(), response.getId());
        Assert.assertEquals(data.getName(), response.getName());
        Assert.assertEquals(data.getAccountNumber(), response.getAccountNumber());
        Assert.assertEquals(data.getSortCode(), response.getSortCode());
        Assert.assertEquals(data.getProfilePhoto(), response.getProfilePhoto());
    }

    @Test
    public void contactDetailsResponseToLootContactEntity() throws Exception {
        ContactEntity entity = ContactDetailsResponse.parseToLootContactEntityObject(response);

        Assert.assertEquals(entity.getId(), response.getId());
        Assert.assertEquals(entity.getName(), response.getName());
        Assert.assertEquals(entity.getAccountNumber(), response.getAccountNumber());
        Assert.assertEquals(entity.getSortCode(), response.getSortCode());
        Assert.assertEquals(entity.getProfilePhoto(), response.getProfilePhoto());
    }

    @Test
    public void contactDetailsResponseToSavedContactEntity() throws Exception {
        SavedContactEntity entity = ContactDetailsResponse.parseToSavedContactEntityObject(response);

        Assert.assertEquals(entity.getId(), response.getId());
        Assert.assertEquals(entity.getName(), response.getName());
        Assert.assertEquals(entity.getAccountNumber(), response.getAccountNumber());
        Assert.assertEquals(entity.getSortCode(), response.getSortCode());
        Assert.assertEquals(entity.getProfilePhoto(), response.getProfilePhoto());
    }


    @Test
    public void savingGoalShouldntHaveNulls() throws Exception {
        Contact data = new Contact();

        Assert.assertNotNull(data.getContactId());
        Assert.assertNotNull(data.getName());
        Assert.assertNotNull(data.getAccountNumber());
        Assert.assertNotNull(data.getSortCode());
        Assert.assertNotNull(data.getProfilePhoto());
    }

    @Test
    public void savingGoalShouldntHaveNullsAfterParsing() throws Exception {
        response.setId(null);
        response.setName(null);
        response.setAccountNumber(null);
        response.setSortCode(null);
        response.setProfilePhoto(null);

        Contact data = ContactDetailsResponse.parseToDataObject(response);

        Assert.assertNotNull(data.getContactId());
        Assert.assertNotNull(data.getName());
        Assert.assertNotNull(data.getAccountNumber());
        Assert.assertNotNull(data.getSortCode());
        Assert.assertNotNull(data.getProfilePhoto());
    }
}

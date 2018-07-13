package io.loot.lootsdk.models;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import io.loot.lootsdk.models.data.userinfo.PersonalDetails;
import io.loot.lootsdk.models.networking.user.userinfo.AddressResponse;
import io.loot.lootsdk.models.networking.user.userinfo.PersonalDetailsResponse;
import io.loot.lootsdk.models.orm.PersonalDetailsEntity;

public class PersonalDetailsTests {

    private PersonalDetailsResponse response;

    @Before
    public void setUp() {
        String titleCode = "titleCode";
        String firstName = "firstName";
        String lastName = "lastName";
        String middleName = "middleName";
        String birthDate = "birthDate";
        String gender = "gender";
        String mobileNumber = "mobileNumber";
        AddressResponse address = new AddressResponse();
        String preferredName = "prefferedName";
        String profilePhoto = "profilePhoto";

        response = new PersonalDetailsResponse();
        response.setTitleCode(titleCode);
        response.setFirstName(firstName);
        response.setLastName(lastName);
        response.setMiddleName(middleName);
        response.setBirthDate(birthDate);
        response.setGender(gender);
        response.setMobileNumber(mobileNumber);
        response.setAddress(address);
        response.setPreferredName(preferredName);
        response.setProfilePhoto(profilePhoto);
    }

    @Test
    public void personalDetailsResponseToData() throws Exception {
        PersonalDetails data = PersonalDetailsResponse.parseToDataObject(response);

        Assert.assertEquals(data.getTitleCode(), response.getTitleCode());
        Assert.assertEquals(data.getFirstName(), response.getFirstName());
        Assert.assertEquals(data.getLastName(), response.getLastName());
        Assert.assertEquals(data.getMiddleName(), response.getMiddleName());
        Assert.assertEquals(data.getBirthDate(), response.getBirthDate());
        Assert.assertEquals(data.getGender(), response.getGender());
        Assert.assertEquals(data.getMobileNumber(), response.getMobileNumber());
        Assert.assertEquals(data.getPreferredName(), response.getPreferredName());
        Assert.assertEquals(data.getProfilePhoto(), response.getProfilePhoto());
    }

    @Test
    public void personalDetailsResponseToEntity() throws Exception {
        PersonalDetailsEntity entity = PersonalDetailsResponse.parseToEntityObject(response);

        Assert.assertEquals(entity.getTitleCode(), response.getTitleCode());
        Assert.assertEquals(entity.getFirstName(), response.getFirstName());
        Assert.assertEquals(entity.getLastName(), response.getLastName());
        Assert.assertEquals(entity.getMiddleName(), response.getMiddleName());
        Assert.assertEquals(entity.getBirthDate(), response.getBirthDate());
        Assert.assertEquals(entity.getGender(), response.getGender());
        Assert.assertEquals(entity.getMobileNumber(), response.getMobileNumber());
        Assert.assertEquals(entity.getPreferredName(), response.getPreferredName());
        Assert.assertEquals(entity.getProfilePhoto(), response.getProfilePhoto());
    }

    @Test
    public void personalDetailsShouldntHaveNulls() throws Exception {
        PersonalDetails data = new PersonalDetails();

        Assert.assertNotNull(data.getTitleCode());
        Assert.assertNotNull(data.getFirstName());
        Assert.assertNotNull(data.getLastName());
        Assert.assertNotNull(data.getMiddleName());
        Assert.assertNotNull(data.getBirthDate());
        Assert.assertNotNull(data.getGender());
        Assert.assertNotNull(data.getMobileNumber());
        Assert.assertNotNull(data.getPreferredName());
        Assert.assertNotNull(data.getProfilePhoto());
        Assert.assertNotNull(data.getAddress());
    }

    @Test
    public void personalDetailsShouldntHaveNullsAfterParse() throws Exception {
        response.setTitleCode(null);
        response.setFirstName(null);
        response.setLastName(null);
        response.setMiddleName(null);
        response.setBirthDate(null);
        response.setGender(null);
        response.setMobileNumber(null);
        response.setAddress(null);
        response.setPreferredName(null);
        response.setProfilePhoto(null);

        PersonalDetails data = PersonalDetailsResponse.parseToDataObject(response);
        Assert.assertNotNull(data.getTitleCode());
        Assert.assertNotNull(data.getFirstName());
        Assert.assertNotNull(data.getLastName());
        Assert.assertNotNull(data.getMiddleName());
        Assert.assertNotNull(data.getBirthDate());
        Assert.assertNotNull(data.getGender());
        Assert.assertNotNull(data.getMobileNumber());
        Assert.assertNotNull(data.getPreferredName());
        Assert.assertNotNull(data.getProfilePhoto());
        Assert.assertNotNull(data.getAddress());
    }

}

package io.loot.lootsdk.models;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import io.loot.lootsdk.models.data.userinfo.UserInfo;
import io.loot.lootsdk.models.networking.user.userinfo.PersonalDetailsResponse;
import io.loot.lootsdk.models.networking.user.userinfo.UserInfoResponse;
import io.loot.lootsdk.models.orm.UserInfoEntity;

public class UserInfoTests {

    private UserInfoResponse response;

    @Before
    public void setUp() {
        String email = "email";
        String countryCode = "countryCode";
        PersonalDetailsResponse personalDetails = new PersonalDetailsResponse();
        boolean legend = true;

        response = new UserInfoResponse();
        response.setEmail(email);
        response.setCountryCode(countryCode);
        response.setPersonalDetails(personalDetails);
        response.setLegend(legend);
    }

    @Test
    public void userInfoResponseToData() throws Exception {
        UserInfo data = UserInfoResponse.parseToDataObject(response);

        Assert.assertEquals(data.getEmail(), response.getEmail());
        Assert.assertEquals(data.getCountryCode(), response.getCountryCode());
        Assert.assertEquals(data.isLegend(), response.isLegend());
    }

    @Test
    public void userInfoResponseToEntity() throws Exception {
        UserInfoEntity entity = UserInfoResponse.parseToEntityObject(response);

        Assert.assertEquals(entity.getEmail(), response.getEmail());
        Assert.assertEquals(entity.getCountryCode(), response.getCountryCode());
        Assert.assertEquals(entity.isLegend(), response.isLegend());
    }

    @Test
    public void userInfoShouldntHaveNulls() throws Exception {
        UserInfo data = new UserInfo();

        Assert.assertNotNull(data.getCountryCode());
        Assert.assertNotNull(data.getEmail());
        Assert.assertNotNull(data.getPersonalDetails());
        Assert.assertNotNull(data.isLegend());
    }

    @Test
    public void userInfoShouldntHaveNullsAfterParsing() throws Exception {
        response.setEmail(null);
        response.setCountryCode(null);
        response.setPersonalDetails(null);

        UserInfo data = UserInfoResponse.parseToDataObject(response);
        Assert.assertNotNull(data.getCountryCode());
        Assert.assertNotNull(data.getEmail());
        Assert.assertNotNull(data.getPersonalDetails());
        Assert.assertNotNull(data.isLegend());
    }

}

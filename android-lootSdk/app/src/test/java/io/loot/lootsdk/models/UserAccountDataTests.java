package io.loot.lootsdk.models;

import org.junit.Assert;
import org.junit.Test;

import io.loot.lootsdk.models.data.userinfo.UserAccountData;

public class UserAccountDataTests {

    @Test
    public void userAccountDataShouldntHaveNulls() throws Exception {
        UserAccountData userAccountData = new UserAccountData();

        Assert.assertNotNull(userAccountData.getDisplayName());
        Assert.assertNotNull(userAccountData.getEmail());
        Assert.assertNotNull(userAccountData.getPhoneNumber());
        Assert.assertNotNull(userAccountData.getPhotoUri());
    }

    @Test
    public void userAccountDataShouldnyHaveNullsAfterSettingIt() throws Exception {
        UserAccountData userAccountData = new UserAccountData();

        userAccountData.setEmail(null);
        userAccountData.setDisplayName(null);
        userAccountData.setPhoneNumber(null);
        userAccountData.setPhotoUri(null);

        Assert.assertNotNull(userAccountData.getDisplayName());
        Assert.assertNotNull(userAccountData.getEmail());
        Assert.assertNotNull(userAccountData.getPhoneNumber());
        Assert.assertNotNull(userAccountData.getPhotoUri());
    }

}

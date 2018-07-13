package io.loot.lootsdk.models;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import io.loot.lootsdk.models.data.userinfo.AccountDetails;
import io.loot.lootsdk.models.networking.user.userinfo.AccountDetailsResponse;
import io.loot.lootsdk.models.networking.user.userinfo.AccountResponse;

public class AccountDetailsTests {

    private AccountDetailsResponse response;

    @Before
    public void setUp() {
        response = new AccountDetailsResponse();
        response.setAccounts(new ArrayList<AccountResponse>());
    }

    @Test
    public void accountDetailsResponseToDataShouldntHaveNulls() throws Exception {
        AccountDetails data = AccountDetailsResponse.parseToDataObject(response);

        Assert.assertNotNull(data.getAccounts());
    }

    @Test
    public void accountDetailsAfterParsingShouldntHaveNulls() throws Exception {
        response.setAccounts(null);

        AccountDetails data = AccountDetailsResponse.parseToDataObject(response);
        Assert.assertNotNull(data.getAccounts());
    }

    @Test
    public void accountDetailsShouldntHaveNullsAfterCreation() throws Exception {
        AccountDetails data = new AccountDetails();
        Assert.assertNotNull(data.getAccounts());
    }

    @Test
    public void accountDetailsShouldntHaveNullsAfterCreationWithParams() throws Exception {
        AccountDetails data = new AccountDetails();
        Assert.assertNotNull(data.getAccounts());
    }

}

package io.loot.lootsdk.models;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import io.loot.lootsdk.models.data.userinfo.Account;
import io.loot.lootsdk.models.networking.user.userinfo.AccountResponse;
import io.loot.lootsdk.models.networking.user.userinfo.ProviderResponse;
import io.loot.lootsdk.models.networking.user.userinfo.UserInfoResponse;
import io.loot.lootsdk.models.orm.AccountEntity;

public class AccountTests {

    private AccountResponse response;

    @Before
    public void setUp() {
        String id = "id";
        String userId = "userid";
        String providerId = "providerid";
        float accountBalance = 10.9f;
        String accountNumber = "accountnumber";
        String sortCode = "sortcode";
        UserInfoResponse userInfo = new UserInfoResponse();
        ProviderResponse provider = new ProviderResponse();

        response = new AccountResponse();
        response.setId(id);
        response.setUserId(userId);
        response.setProviderId(providerId);
        response.setAccountBalance(accountBalance);
        response.setAccountNumber(accountNumber);
        response.setSortCode(sortCode);
        response.setUserInfo(userInfo);
        response.setProvider(provider);
    }

    @Test
    public void accountResponseToData() throws Exception {
        Account data = AccountResponse.parseToDataObject(response);

        Assert.assertEquals(data.getId(), response.getId());
        Assert.assertEquals(data.getUserId(), response.getUserId());
        Assert.assertEquals(data.getAccountBalance(), response.getAccountBalance(), 0.1f);
        Assert.assertEquals(data.getAccountNumber(), response.getAccountNumber());
        Assert.assertEquals(data.getSortCode(), response.getSortCode());
    }

    @Test
    public void accountResponseToEntity() throws Exception {
        AccountEntity entity = AccountResponse.parseToEntityObject(response);

        Assert.assertEquals(entity.getId(), response.getId());
        Assert.assertEquals(entity.getUserId(), response.getUserId());
        Assert.assertEquals(entity.getAccountBalance(), response.getAccountBalance(), 0.1f);
        Assert.assertEquals(entity.getAccountNumber(), response.getAccountNumber());
        Assert.assertEquals(entity.getSortCode(), response.getSortCode());
    }

    @Test
    public void accountDataShouldntHaveNulls() throws Exception {
        Account data = new Account();

        Assert.assertNotNull(data.getId());
        Assert.assertNotNull(data.getUserId());
        Assert.assertNotNull(data.getAccountBalance());
        Assert.assertNotNull(data.getAccountNumber());
        Assert.assertNotNull(data.getSortCode());
        Assert.assertNotNull(data.getProvider());
        Assert.assertNotNull(data.getUserInfo());
    }

    @Test
    public void accountDataShouldntHaveNullsAfterParsing() throws Exception {
        response.setId(null);
        response.setUserId(null);
        response.setProviderId(null);
        response.setAccountBalance(0f);
        response.setAccountNumber(null);
        response.setSortCode(null);
        response.setProvider(null);
        response.setUserInfo(null);

        Account data = AccountResponse.parseToDataObject(response);
        Assert.assertNotNull(data.getId());
        Assert.assertNotNull(data.getUserId());
        Assert.assertNotNull(data.getAccountBalance());
        Assert.assertNotNull(data.getAccountNumber());
        Assert.assertNotNull(data.getSortCode());
        Assert.assertNotNull(data.getProvider());
        Assert.assertNotNull(data.getUserInfo());
    }

}

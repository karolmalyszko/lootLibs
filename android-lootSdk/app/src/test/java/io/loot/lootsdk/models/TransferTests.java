package io.loot.lootsdk.models;

import org.junit.Assert;
import org.junit.Test;

import io.loot.lootsdk.models.data.transfer.Transfer;

public class TransferTests {

    @Test
    public void transferShouldntHaveNulls() throws Exception {
        Transfer transfer = new Transfer();

        Assert.assertNotNull(transfer.getAmount());
        Assert.assertNotNull(transfer.getRecipentAccountNumber());
        Assert.assertNotNull(transfer.getRecipentName());
        Assert.assertNotNull(transfer.getRecipentSortCode());
        Assert.assertNotNull(transfer.getReference());
    }

    @Test
    public void transferShouldntHaveNullsAfterConstructing() throws Exception {
        Transfer transfer = new Transfer(null, null, null, 0, null);

        Assert.assertNotNull(transfer.getAmount());
        Assert.assertNotNull(transfer.getRecipentAccountNumber());
        Assert.assertNotNull(transfer.getRecipentName());
        Assert.assertNotNull(transfer.getRecipentSortCode());
        Assert.assertNotNull(transfer.getReference());
    }

    @Test
    public void transferShouldntHaveNullsAfterSettingIt() throws Exception {
        Transfer transfer = new Transfer();
        transfer.setRecipentAccountNumber(null);
        transfer.setRecipentName(null);
        transfer.setRecipentSortCode(null);
        transfer.setReference(null);
        transfer.setAmount(0);

        Assert.assertNotNull(transfer.getAmount());
        Assert.assertNotNull(transfer.getRecipentAccountNumber());
        Assert.assertNotNull(transfer.getRecipentName());
        Assert.assertNotNull(transfer.getRecipentSortCode());
        Assert.assertNotNull(transfer.getReference());
    }

}

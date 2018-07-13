package io.loot.lootsdk.models;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import io.loot.lootsdk.models.data.transactions.Transaction;
import io.loot.lootsdk.models.networking.category.CategoryResponse;
import io.loot.lootsdk.models.networking.transactions.MerchantResponse;
import io.loot.lootsdk.models.networking.transactions.TransactionResponse;
import io.loot.lootsdk.models.orm.TransactionEntity;

public class TransactionTests {

    private TransactionResponse response;

    @Before
    public void setUp() {
        String dateText = "10.10.2010";
        String id = "id";
        String description = "desc";
        String localAmount = "localamount";
        String localCurrency = "localcurrency";
        String settlementAmount = "settlementAmount";
        String direction = "direction";
        String authorisationDate = "20120617154536";
        String localDate = "20120617154536";
        String settlementDate = "20120617154536";
        String postTransactionBalance = "postbalance";
        String transactionType = "type";
        String budgetStatus = "budgetStatus";
        String transactionStatus = "transactionStatus";
        String mmc = "mmc";
        CategoryResponse category = new CategoryResponse();
        CategoryResponse foundCategory = new CategoryResponse();
        String accountId = "acccountId";
        MerchantResponse merchant = new MerchantResponse();

        response = new TransactionResponse();
        response.setDateText(dateText);
        response.setId(id);
        response.setDescription(description);
        response.setLocalAmount(localAmount);
        response.setLocalCurrency(localCurrency);
        response.setSettlementAmount(settlementAmount);
        response.setDirection(direction);
        response.setAuthorisationDate(authorisationDate);
        response.setLocalDate(localDate);
        response.setSettlementDate(settlementDate);
        response.setPostTransactionBalance(postTransactionBalance);
        response.setTransactionType(transactionType);
        response.setBudgetStatus(budgetStatus);
        response.setTransactionStatus(transactionStatus);
        response.setMmc(mmc);
        response.setCategory(category);
        response.setFoundCategory(foundCategory);
        response.setAccountId(accountId);
        response.setMerchant(merchant);
    }

    @Test
    public void transactionResponseToData() throws Exception {
        Transaction data = TransactionResponse.parseToDataObject(response);

        Assert.assertEquals(data.getDateText(), response.getDateText());
        Assert.assertEquals(data.getId(), response.getId());
        Assert.assertEquals(data.getDescription(), response.getDescription());
        Assert.assertEquals(data.getLocalAmount(), response.getLocalAmount());
        Assert.assertEquals(data.getLocalCurrency(), response.getLocalCurrency());
        Assert.assertEquals(data.getSettlementAmount(), response.getSettlementAmount());
        Assert.assertEquals(data.getDirection(), response.getDirection());
        Assert.assertEquals(data.getAuthorisationDate(), response.getAuthorisationDate());
        Assert.assertEquals(data.getLocalDate(), response.getLocalDate());
        Assert.assertEquals(data.getSettlementDate(), response.getSettlementDate());
        Assert.assertEquals(data.getPostTransactionBalance(), response.getPostTransactionBalance());
        Assert.assertEquals(data.getTransactionType(), response.getTransactionType());
        Assert.assertEquals(data.getBudgetStatus(), response.getBudgetStatus());
        Assert.assertEquals(data.getTransactionStatus(), response.getTransactionStatus());
        Assert.assertEquals(data.getMmc(), response.getMmc());
        Assert.assertEquals(data.getAccountId(), response.getAccountId());
    }

    @Test
    public void transactionResponseToEntity() throws Exception {
        TransactionEntity entity = TransactionResponse.parseToEntityObject(response);

        Assert.assertEquals(entity.getId(), response.getId());
        Assert.assertEquals(entity.getDescription(), response.getDescription());
        Assert.assertEquals(entity.getLocalAmount(), response.getLocalAmount());
        Assert.assertEquals(entity.getLocalCurrency(), response.getLocalCurrency());
        Assert.assertEquals(entity.getSettlementAmount(), response.getSettlementAmount());
        Assert.assertEquals(entity.getDirection(), response.getDirection());
        Assert.assertEquals(entity.getAuthorisationDate(), response.getAuthorisationDate());
        Assert.assertEquals(entity.getLocalDate(), response.getLocalDate());
        Assert.assertEquals(entity.getSettlementDate(), response.getSettlementDate());
        Assert.assertEquals(entity.getPostTransactionBalance(), response.getPostTransactionBalance());
        Assert.assertEquals(entity.getTransactionType(), response.getTransactionType());
        Assert.assertEquals(entity.getBudgetStatus(), response.getBudgetStatus());
        Assert.assertEquals(entity.getTransactionStatus(), response.getTransactionStatus());
        Assert.assertEquals(entity.getMmc(), response.getMmc());
        Assert.assertEquals(entity.getAccountId(), response.getAccountId());
    }

    @Test
    public void transactionDataShouldntHaveNulls() throws Exception {
        Transaction data = new Transaction();

        Assert.assertNotNull(data.getDateText());
        Assert.assertNotNull(data.getId());
        Assert.assertNotNull(data.getDescription());
        Assert.assertNotNull(data.getLocalAmount());
        Assert.assertNotNull(data.getLocalCurrency());
        Assert.assertNotNull(data.getSettlementAmount());
        Assert.assertNotNull(data.getDirection());
        Assert.assertNotNull(data.getAuthorisationDate());
        Assert.assertNotNull(data.getLocalDate());
        Assert.assertNotNull(data.getSettlementDate());
        Assert.assertNotNull(data.getPostTransactionBalance());
        Assert.assertNotNull(data.getTransactionType());
        Assert.assertNotNull(data.getBudgetStatus());
        Assert.assertNotNull(data.getTransactionStatus());
        Assert.assertNotNull(data.getMmc());
        Assert.assertNotNull(data.getAccountId());
        Assert.assertNotNull(data.getCategory());
        Assert.assertNotNull(data.getFoundCategory());
        Assert.assertNotNull(data.getMerchant());
    }

    @Test
    public void transactionDataShouldntHaveNullsAfterParsing() throws Exception {
        response = new TransactionResponse();
        response.setDateText(null);
        response.setId(null);
        response.setDescription(null);
        response.setLocalAmount(null);
        response.setLocalCurrency(null);
        response.setSettlementAmount(null);
        response.setDirection(null);
        response.setAuthorisationDate(null);
        response.setLocalDate(null);
        response.setSettlementDate(null);
        response.setPostTransactionBalance(null);
        response.setTransactionType(null);
        response.setBudgetStatus(null);
        response.setTransactionStatus(null);
        response.setMmc(null);
        response.setCategory(null);
        response.setFoundCategory(null);
        response.setAccountId(null);
        response.setMerchant(null);

        Transaction data = TransactionResponse.parseToDataObject(response);
        Assert.assertNotNull(data.getDateText());
        Assert.assertNotNull(data.getId());
        Assert.assertNotNull(data.getDescription());
        Assert.assertNotNull(data.getLocalAmount());
        Assert.assertNotNull(data.getLocalCurrency());
        Assert.assertNotNull(data.getSettlementAmount());
        Assert.assertNotNull(data.getDirection());
        Assert.assertNotNull(data.getAuthorisationDate());
        Assert.assertNotNull(data.getLocalDate());
        Assert.assertNotNull(data.getSettlementDate());
        Assert.assertNotNull(data.getPostTransactionBalance());
        Assert.assertNotNull(data.getTransactionType());
        Assert.assertNotNull(data.getBudgetStatus());
        Assert.assertNotNull(data.getTransactionStatus());
        Assert.assertNotNull(data.getMmc());
        Assert.assertNotNull(data.getAccountId());
        Assert.assertNotNull(data.getCategory());
        Assert.assertNotNull(data.getFoundCategory());
        Assert.assertNotNull(data.getMerchant());
    }

}

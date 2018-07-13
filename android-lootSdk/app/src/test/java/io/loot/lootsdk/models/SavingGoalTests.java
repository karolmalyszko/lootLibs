package io.loot.lootsdk.models;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import io.loot.lootsdk.models.data.SavingGoal;
import io.loot.lootsdk.models.networking.savingGoals.SavingsGoalResponse;
import io.loot.lootsdk.models.orm.SavingsGoalEntity;

public class SavingGoalTests {

    private SavingsGoalResponse response;

    @Before
    public void setUp() {
        String id = "id";
        String name = "name";
        String description = "desc";
        String balance = "balance";
        String goalAmount = "amount";
        String saveBy = "saveby";
        String imageUrl = "imageurl";
        String status = "status";
        float goalPercentage = 0.431f;

        response = new SavingsGoalResponse();
        response.setId(id);
        response.setName(name);
        response.setDescription(description);
        response.setBalance(balance);
        response.setGoalAmount(goalAmount);
        response.setSaveBy(saveBy);
        response.setImageUrl(imageUrl);
        response.setStatus(status);
        response.setGoalPercentage(goalPercentage);
    }

    @Test
    public void savingGoalResponseToData() throws Exception {
        SavingGoal data = SavingsGoalResponse.parseToDataObject(response);

        Assert.assertEquals(data.getId(), response.getId());
        Assert.assertEquals(data.getName(), response.getName());
        Assert.assertEquals(data.getDescription(), response.getDescription());
        Assert.assertEquals(data.getBalance(), response.getBalance());
        Assert.assertEquals(data.getGoalAmount(), response.getGoalAmount());
        Assert.assertEquals(data.getSaveBy(), response.getSaveBy());
        Assert.assertEquals(data.getImageUrl(), response.getImageUrl());
        Assert.assertEquals(data.getStatus(), response.getStatus());
        Assert.assertEquals(data.getGoalPercentage(), response.getGoalPercentage(), 0.1f);
    }

    @Test
    public void savingGoalResponseToEntity() throws Exception {
        SavingsGoalEntity entity = SavingsGoalResponse.parseToEntityObject(response);

        Assert.assertEquals(entity.getId(), response.getId());
        Assert.assertEquals(entity.getName(), response.getName());
        Assert.assertEquals(entity.getDescription(), response.getDescription());
        Assert.assertEquals(entity.getBalance(), response.getBalance());
        Assert.assertEquals(entity.getGoalAmount(), response.getGoalAmount());
        Assert.assertEquals(entity.getSaveBy(), response.getSaveBy());
        Assert.assertEquals(entity.getImageUrl(), response.getImageUrl());
        Assert.assertEquals(entity.getStatus(), response.getStatus());
        Assert.assertEquals(entity.getGoalPercentage(), response.getGoalPercentage(), 0.1f);
    }

    @Test
    public void savingGoalShouldntHaveNulls() throws Exception {
        SavingGoal data = new SavingGoal();

        Assert.assertNotNull(data.getId());
        Assert.assertNotNull(data.getName());
        Assert.assertNotNull(data.getDescription());
        Assert.assertNotNull(data.getBalance());
        Assert.assertNotNull(data.getGoalAmount());
        Assert.assertNotNull(data.getSaveBy());
        Assert.assertNotNull(data.getImageUrl());
        Assert.assertNotNull(data.getStatus());
        Assert.assertNotNull(data.getGoalPercentage());
    }

    @Test
    public void savingGoalShouldntHaveNullsAfterParsing() throws Exception {
        response.setId(null);
        response.setName(null);
        response.setDescription(null);
        response.setBalance(null);
        response.setGoalAmount(null);
        response.setSaveBy(null);
        response.setImageUrl(null);
        response.setStatus(null);
        response.setGoalPercentage(1.18181f);

        SavingGoal data = SavingsGoalResponse.parseToDataObject(response);

        Assert.assertNotNull(data.getId());
        Assert.assertNotNull(data.getName());
        Assert.assertNotNull(data.getDescription());
        Assert.assertNotNull(data.getBalance());
        Assert.assertNotNull(data.getGoalAmount());
        Assert.assertNotNull(data.getSaveBy());
        Assert.assertNotNull(data.getImageUrl());
        Assert.assertNotNull(data.getStatus());
        Assert.assertNotNull(data.getGoalPercentage());
    }

}

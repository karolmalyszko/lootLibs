package io.loot.lootsdk.models;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import io.loot.lootsdk.models.data.Budget;
import io.loot.lootsdk.models.networking.user.BudgetResponse;
import io.loot.lootsdk.models.orm.BudgetEntity;

public class BudgetTests {

    private BudgetResponse response;

    @Before
    public void setUp() {
        String dailyBudget = "budget";
        String leftToSpendToday = "leftToSpend";
        float dailyPercentage = 1.34f;
        String weeklyBudget = "weekly";

        response = new BudgetResponse();
        response.setDailyBudget(dailyBudget);
        response.setLeftToSpendToday(leftToSpendToday);
        response.setDailyPercentage(dailyPercentage);
        response.setWeeklyBudget(weeklyBudget);
    }

    @Test
    public void budgetResponseToData() throws Exception {
        Budget data = BudgetResponse.parseToDataObject(response);

        Assert.assertEquals(data.getDailyBudget(), response.getDailyBudget());
        Assert.assertEquals(data.getLeftToSpendToday(), response.getLeftToSpendToday());
        Assert.assertEquals(data.getDailyPercentage(), response.getDailyPercentage(), 0.1f);
        Assert.assertEquals(data.getWeeklyBudget(), response.getWeeklyBudget());
    }

    @Test
    public void budgetResponseToEntity() throws Exception {
        BudgetEntity entity = BudgetResponse.parseToEntityObject(response);

        Assert.assertEquals(entity.getDailyBudget(), response.getDailyBudget());
        Assert.assertEquals(entity.getLeftToSpendToday(), response.getLeftToSpendToday());
        Assert.assertEquals(entity.getDailyPercentage(), response.getDailyPercentage(), 0.1f);
        Assert.assertEquals(entity.getWeeklyBudget(), response.getWeeklyBudget());
    }

    @Test
    public void budgetShouldntHaveNulls() throws Exception {
        Budget data = new Budget();

        Assert.assertNotNull(data.getDailyBudget());
        Assert.assertNotNull(data.getLeftToSpendToday());
        Assert.assertNotNull(data.getDailyPercentage());
        Assert.assertNotNull(data.getWeeklyBudget());
    }

    @Test
    public void budgetShouldntHaveNullsAfterParsing() throws Exception {
        response.setDailyBudget(null);
        response.setLeftToSpendToday(null);
        response.setDailyPercentage(0.1f);
        response.setWeeklyBudget(null);

        Budget data = BudgetResponse.parseToDataObject(response);

        Assert.assertNotNull(data.getDailyBudget());
        Assert.assertNotNull(data.getLeftToSpendToday());
        Assert.assertNotNull(data.getDailyPercentage());
        Assert.assertNotNull(data.getWeeklyBudget());
    }

}

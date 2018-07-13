package io.loot.lootsdk.models.data;

public class Budget {

    private String dailyBudget;
    private String leftToSpendToday;
    private float dailyPercentage;
    private String weeklyBudget;

    public Budget() {
        this.dailyBudget = "";
        this.leftToSpendToday = "";
        this.dailyPercentage = 0;
        this.weeklyBudget = "";
    }

    public Budget(Budget budget) {
        this.dailyBudget = budget.getDailyBudget();
        this.leftToSpendToday = budget.getLeftToSpendToday();
        this.dailyPercentage = budget.getDailyPercentage();
        this.weeklyBudget = budget.getWeeklyBudget();
    }

    public String getDailyBudget() {
        return dailyBudget;
    }

    public void setDailyBudget(String dailyBudget) {
        if (dailyBudget == null) {
            dailyBudget = "";
        }

        this.dailyBudget = dailyBudget;
    }

    public String getLeftToSpendToday() {
        return leftToSpendToday;
    }

    public void setLeftToSpendToday(String leftToSpendToday) {
        if (leftToSpendToday == null) {
            leftToSpendToday = "";
        }

        this.leftToSpendToday = leftToSpendToday;
    }

    public float getDailyPercentage() {
        return dailyPercentage;
    }

    public void setDailyPercentage(float dailyPercentage) {
        this.dailyPercentage = dailyPercentage;
    }

    public String getWeeklyBudget() {
        return weeklyBudget;
    }

    public void setWeeklyBudget(String weeklyBudget) {
        if (weeklyBudget == null) {
            weeklyBudget = "";
        }

        this.weeklyBudget = weeklyBudget;
    }

}

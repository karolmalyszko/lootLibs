package io.loot.lootsdk.models.orm;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import io.loot.lootsdk.models.data.SavingGoal;

@Entity(tableName = "SavingGoal")
public class SavingsGoalEntity {

    @PrimaryKey
    @NonNull
    private String id = "0";
    private String name;
    private String description;
    private String balance;
    private String goalAmount;
    private String saveBy;
    private String imageUrl;
    private String status;
    private float goalPercentage;

    public SavingGoal parseToDataObject() {
        SavingGoal savingGoal = new SavingGoal();
        savingGoal.setId(id);
        savingGoal.setName(name);
        savingGoal.setDescription(description);
        savingGoal.setBalance(balance);
        savingGoal.setGoalAmount(goalAmount);
        savingGoal.setSaveBy(saveBy);
        savingGoal.setImageUrl(imageUrl);
        savingGoal.setStatus(status);
        savingGoal.setGoalPercentage(goalPercentage);

        return savingGoal;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBalance() {
        return this.balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getGoalAmount() {
        return this.goalAmount;
    }

    public void setGoalAmount(String goalAmount) {
        this.goalAmount = goalAmount;
    }

    public String getSaveBy() {
        return this.saveBy;
    }

    public void setSaveBy(String saveBy) {
        this.saveBy = saveBy;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public float getGoalPercentage() {
        return this.goalPercentage;
    }

    public void setGoalPercentage(float goalPercentage) {
        this.goalPercentage = goalPercentage;
    }

}

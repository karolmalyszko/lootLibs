package io.loot.lootsdk.models.data;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.Serializable;

public class SavingGoal implements Serializable {

    private String id;
    private String name;
    private String description;
    private String balance;
    private String goalAmount;
    private String saveBy;
    private String imageUrl;
    private String status;
    private float goalPercentage;

    public SavingGoal() {
        this.id = "";
        this.name = "";
        this.description = "";
        this.balance = "";
        this.goalAmount = "";
        this.setCreationDate("");
        this.imageUrl = "";
        this.status = "";
        this.goalPercentage = 0;
    }

    public SavingGoal(SavingGoal savingGoal) {
        this.id = savingGoal.getId();
        this.name = savingGoal.getName();
        this.description = savingGoal.getDescription();
        this.balance = savingGoal.getBalance();
        this.goalAmount = savingGoal.getGoalAmount();
        this.setCreationDate(savingGoal.getCreationDate());
        this.imageUrl = savingGoal.getImageUrl();
        this.status = savingGoal.getStatus();
        this.goalPercentage = savingGoal.getGoalPercentage();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (id == null) {
            id = "";
        }
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null) {
            name = "";
        }
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description == null) {
            description = "";
        }
        this.description = description;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        if (balance == null) {
            balance = "";
        }
        this.balance = balance;
    }

    public String getGoalAmount() {
        return goalAmount;
    }

    public void setGoalAmount(String goalAmount) {
        if (goalAmount == null) {
            goalAmount = "";
        }
        this.goalAmount = goalAmount;
    }

    public String getSaveBy() {
        return saveBy;
    }

    public void setSaveBy(String saveBy) {
        if (saveBy == null) {
            saveBy = "";
        }
        this.saveBy = saveBy;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        if (imageUrl == null) {
            imageUrl = "";
        }
        this.imageUrl = imageUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if (status == null) {
            status = "";
        }
        this.status = status;
    }

    public float getGoalPercentage() {
        return goalPercentage;
    }

    public void setGoalPercentage(float goalPercentage) {
        this.goalPercentage = goalPercentage;
    }

    public String getCreationDate(){
        if (getSaveBy().isEmpty()) {
            return getSaveBy();
        }

        DateTimeFormatter dtfOut = DateTimeFormat.forPattern("dd MMM yyyy");
        return dtfOut.print(DateTime.parse(getSaveBy()));
    }

    public void setCreationDate(String date){
        DateTimeFormatter dtfIn = DateTimeFormat.forPattern(" dd MMM yyyy");
        DateTimeFormatter dtfOut = DateTimeFormat.forPattern("dd-MM-yyyy");

        if (date == null || date.isEmpty()) {
            setSaveBy(new DateTime().toString());
            return;
        }

        setSaveBy(dtfOut.print(dtfIn.parseDateTime(date)));
    }
}

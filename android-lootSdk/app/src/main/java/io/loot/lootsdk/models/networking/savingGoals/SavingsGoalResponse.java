package io.loot.lootsdk.models.networking.savingGoals;

import com.google.gson.annotations.SerializedName;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.Serializable;

import io.loot.lootsdk.models.data.SavingGoal;
import io.loot.lootsdk.models.orm.SavingsGoalEntity;
import lombok.Data;

@Data
public class SavingsGoalResponse implements Serializable {
    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("balance")
    private String balance;

    @SerializedName("goal_amount")
    private String goalAmount;

    @SerializedName("save_by")
    private String saveBy;

    @SerializedName("image_url")
    private String imageUrl;

    @SerializedName("status")
    private String status;

    @SerializedName("goal_percentage")
    private float goalPercentage;

    public String getCreationDate(){
        if (getSaveBy() == null ) {
            return getSaveBy();
        }
        DateTimeFormatter dtfOut = DateTimeFormat.forPattern("dd MMM yyyy");
        return dtfOut.print(DateTime.parse(getSaveBy()));
    }

    public void setCreationDate(String date){
        DateTimeFormatter dtfIn = DateTimeFormat.forPattern(" dd MMM yyyy");
        DateTimeFormatter dtfOut = DateTimeFormat.forPattern("dd-MM-yyyy");
        setSaveBy(dtfOut.print(dtfIn.parseDateTime(date)));
    }

    public static SavingGoal parseToDataObject(SavingsGoalResponse savingsGoalResponse) {
        if (savingsGoalResponse == null) {
            savingsGoalResponse = new SavingsGoalResponse();
        }
        SavingGoal savingGoal = new SavingGoal();
        savingGoal.setId(savingsGoalResponse.getId());
        savingGoal.setStatus(savingsGoalResponse.getStatus());
        savingGoal.setBalance(savingsGoalResponse.getBalance());
        savingGoal.setDescription(savingsGoalResponse.getDescription());
        savingGoal.setGoalAmount(savingsGoalResponse.getGoalAmount());
        savingGoal.setGoalPercentage(savingsGoalResponse.getGoalPercentage());
        savingGoal.setImageUrl(savingsGoalResponse.getImageUrl());
        savingGoal.setName(savingsGoalResponse.getName());
        savingGoal.setSaveBy(savingsGoalResponse.getSaveBy());
        return savingGoal;
    }

    public static SavingsGoalEntity parseToEntityObject(SavingsGoalResponse savingsGoalResponse) {
        if (savingsGoalResponse == null) {
            savingsGoalResponse = new SavingsGoalResponse();
        }
        SavingsGoalEntity savingsGoalEntity = new SavingsGoalEntity();
        savingsGoalEntity.setId(savingsGoalResponse.getId());
        savingsGoalEntity.setStatus(savingsGoalResponse.getStatus());
        savingsGoalEntity.setBalance(savingsGoalResponse.getBalance());
        savingsGoalEntity.setDescription(savingsGoalResponse.getDescription());
        savingsGoalEntity.setGoalAmount(savingsGoalResponse.getGoalAmount());
        savingsGoalEntity.setGoalPercentage(savingsGoalResponse.getGoalPercentage());
        savingsGoalEntity.setImageUrl(savingsGoalResponse.getImageUrl());
        savingsGoalEntity.setName(savingsGoalResponse.getName());
        savingsGoalEntity.setSaveBy(savingsGoalResponse.getSaveBy());
        return savingsGoalEntity;
    }

}

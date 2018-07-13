package io.loot.lootsdk.models.networking.savingGoals;

import com.google.gson.annotations.SerializedName;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.Serializable;

import lombok.Data;

@Data
public class CreateSavingsGoalRequest implements Serializable {
    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("goal_amount")
    private int goalAmount;

    @SerializedName("save_by")
    private String saveBy;

    @SerializedName("image")
    private String image;

    public void setCreationDate(String date){
        DateTimeFormatter dtfIn = DateTimeFormat.forPattern(" dd MMM yyyy");
        DateTimeFormatter dtfOut = DateTimeFormat.forPattern("dd-MM-yyyy");
        setSaveBy(dtfOut.print(dtfIn.parseDateTime(date)));
    }
}

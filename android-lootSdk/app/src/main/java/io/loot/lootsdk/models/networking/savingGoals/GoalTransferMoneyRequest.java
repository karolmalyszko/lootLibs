package io.loot.lootsdk.models.networking.savingGoals;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(suppressConstructorProperties=true)
public class GoalTransferMoneyRequest {
    @SerializedName("amount")
    int amount;
}

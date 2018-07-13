package io.loot.lootsdk.models.networking.topUp;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class TopUp3DSStatusResponse {
    public static String STATUS_SUCCEEDED = "succeeded";
    public static String STATUS_PENDING = "pending";
    public static String STATUS_FAILED = "failed";

    @SerializedName("status")
    String status;
}

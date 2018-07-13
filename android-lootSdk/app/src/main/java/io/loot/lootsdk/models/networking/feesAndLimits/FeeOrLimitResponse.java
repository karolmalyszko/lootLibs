package io.loot.lootsdk.models.networking.feesAndLimits;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.loot.lootsdk.models.data.userinfo.FeeOrLimit;
import io.loot.lootsdk.models.orm.FeeOrLimitEntity;
import lombok.Data;

@Data
public class FeeOrLimitResponse implements Serializable {
    @SerializedName("title")
    private String title;
    @SerializedName("fee")
    private String fee;
    @SerializedName("description")
    private String description;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("updated_at")
    private String updatedAt;

    public static FeeOrLimitEntity parseToEntityObject(FeeOrLimitResponse feeOrLimitResponse) {
        if (feeOrLimitResponse == null) {
            feeOrLimitResponse = new FeeOrLimitResponse();
        }
        FeeOrLimitEntity feeOrLimitEntity = new FeeOrLimitEntity();
        feeOrLimitEntity.setTitle(feeOrLimitResponse.getTitle());
        feeOrLimitEntity.setFee(feeOrLimitResponse.getFee());
        feeOrLimitEntity.setDescription(feeOrLimitResponse.getDescription());
        feeOrLimitEntity.setCreatedAt(feeOrLimitResponse.getCreatedAt());
        feeOrLimitEntity.setUpdatedAt(feeOrLimitResponse.getUpdatedAt());
        return feeOrLimitEntity;
    }

    public static FeeOrLimit parseToDataObject(FeeOrLimitResponse feeOrLimitResponse) {
        if (feeOrLimitResponse == null) {
            feeOrLimitResponse = new FeeOrLimitResponse();
        }
        FeeOrLimit feeOrLimitObject = new FeeOrLimit();
        feeOrLimitObject.setTitle(feeOrLimitResponse.getTitle());
        feeOrLimitObject.setFee(feeOrLimitResponse.getFee());
        feeOrLimitObject.setDescription(feeOrLimitResponse.getDescription());
        feeOrLimitObject.setCreatedAt(feeOrLimitResponse.getCreatedAt());
        feeOrLimitObject.setUpdatedAt(feeOrLimitResponse.getUpdatedAt());
        return feeOrLimitObject;
    }
}

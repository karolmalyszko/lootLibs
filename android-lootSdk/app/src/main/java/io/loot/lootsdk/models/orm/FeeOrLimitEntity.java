package io.loot.lootsdk.models.orm;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import io.loot.lootsdk.models.data.userinfo.FeeOrLimit;

@Entity(tableName = "FeeOrLimit")
public class FeeOrLimitEntity {

    @PrimaryKey(autoGenerate = true)
    private long entityId;
    private String title;
    private String fee;
    private String description;
    private String createdAt;
    private String updatedAt;


    public FeeOrLimit parseToDataObject() {
        FeeOrLimit feeOrLimit = new FeeOrLimit();

        feeOrLimit.setTitle(title);
        feeOrLimit.setDescription(description);
        feeOrLimit.setFee(fee);
        feeOrLimit.setCreatedAt(createdAt);
        feeOrLimit.setUpdatedAt(updatedAt);

        return feeOrLimit;
    }

    public long getEntityId() {
        return this.entityId;
    }

    public void setEntityId(long entityId) {
        this.entityId = entityId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFee() {
        return this.fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}

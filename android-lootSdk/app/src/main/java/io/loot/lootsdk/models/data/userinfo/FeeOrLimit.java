package io.loot.lootsdk.models.data.userinfo;


import java.io.Serializable;

public class FeeOrLimit implements Serializable {

    private String title;
    private String fee;
    private String description;
    private String createdAt;
    private String updatedAt;

    public FeeOrLimit() {
        this.title = "";
        this.fee = "";
        this.description = "";
        this.createdAt = "";
        this.updatedAt = "";
    }

    public FeeOrLimit(FeeOrLimit feeOrLimit) {
        this.title = feeOrLimit.getTitle();
        this.fee = feeOrLimit.getFee();
        this.description = feeOrLimit.getDescription();
        this.createdAt = feeOrLimit.getCreatedAt();
        this.updatedAt = feeOrLimit.getUpdatedAt();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null) {
            title = "";
        }
        this.title = title;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        if (fee == null) {
            fee = "";
        }
        this.fee = fee;
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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        if (createdAt == null) {
            createdAt = "";
        }
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        if (updatedAt == null) {
            updatedAt = "";
        }
        this.updatedAt = updatedAt;
    }
}

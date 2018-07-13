package io.loot.lootsdk.models.networking.category;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.Data;

public @Data
class CategorisationRequest implements Serializable {

    @SerializedName("category_id")
    String category_id;

    public CategorisationRequest(String category_id) {
        this.category_id = category_id;
    }

}

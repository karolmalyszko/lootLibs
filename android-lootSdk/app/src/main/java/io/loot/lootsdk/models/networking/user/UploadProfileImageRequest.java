package io.loot.lootsdk.models.networking.user;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

public @Data class UploadProfileImageRequest {

    @SerializedName("image")
    String image;

    public UploadProfileImageRequest(String image) {
        this.image = image;
    }

}

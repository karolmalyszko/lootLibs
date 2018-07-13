package io.loot.lootsdk.models.networking.user.userinfo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.loot.lootsdk.models.data.userinfo.Provider;
import io.loot.lootsdk.models.orm.ProviderEntity;
import lombok.Data;

public @Data
class ProviderResponse implements Serializable {
    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("priority")
    private String priority;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("updated_at")
    private String updatedAt;

    @SerializedName("url")
    private String url;

    @SerializedName("default")
    private boolean isDefault;

    public static ProviderEntity parseToEntityObject(ProviderResponse providerResponse) {
        if (providerResponse == null) {
            providerResponse = new ProviderResponse();
        }
        ProviderEntity providerEntity = new ProviderEntity();
        providerEntity.setId(providerResponse.getId());
        providerEntity.setName(providerResponse.getName());
        providerEntity.setPriority(providerResponse.getPriority());
        providerEntity.setCreatedAt(providerResponse.getCreatedAt());
        providerEntity.setUpdatedAt(providerResponse.getUpdatedAt());
        providerEntity.setUrl(providerResponse.getUrl());
        providerEntity.setIsDefault(providerResponse.isDefault());
        return providerEntity;
    }

    public static Provider parseToDataObject(ProviderResponse providerResponse) {
        if (providerResponse == null) {
            providerResponse = new ProviderResponse();
        }
        Provider provider = new Provider();
        provider.setId(providerResponse.getId());
        provider.setName(providerResponse.getName());
        provider.setPriority(providerResponse.getPriority());
        provider.setCreatedAt(providerResponse.getCreatedAt());
        provider.setUpdatedAt(providerResponse.getUpdatedAt());
        provider.setUrl(providerResponse.getUrl());
        provider.setDefault(providerResponse.isDefault());
        return provider;
    }
}

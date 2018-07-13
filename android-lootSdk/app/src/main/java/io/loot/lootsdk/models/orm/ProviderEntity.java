package io.loot.lootsdk.models.orm;

import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import io.loot.lootsdk.models.data.userinfo.Provider;

@android.arch.persistence.room.Entity(tableName = "Provider")
public class ProviderEntity {

    @PrimaryKey
    @NonNull
    private String id = "0";
    private String name;
    private String priority;
    private String createdAt;
    private String updatedAt;
    private String url;
    private boolean isDefault;

    public Provider parseToDataObject() {
        Provider provider = new Provider();
        provider.setId(id);
        provider.setName(name);
        provider.setPriority(priority);
        provider.setCreatedAt(createdAt);
        provider.setUpdatedAt(updatedAt);
        provider.setUrl(url);
        provider.setDefault(isDefault);
        return provider;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPriority() {
        return this.priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
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

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean getIsDefault() {
        return this.isDefault;
    }

    public void setIsDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

}

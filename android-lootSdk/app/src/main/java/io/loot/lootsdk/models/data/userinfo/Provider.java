package io.loot.lootsdk.models.data.userinfo;

import java.io.Serializable;

public class Provider implements Serializable {


    private String id;
    private String name;
    private String priority;
    private String createdAt;
    private String updatedAt;
    private String url;
    private boolean isDefault;

    public Provider() {
        this.id = "";
        this.name = "";
        this.priority = "";
        this.createdAt = "";
        this.updatedAt = "";
        this.url = "";
        this.isDefault = false;
    }

    public Provider(Provider provider) {
        this.id = provider.getId();
        this.name = provider.getName();
        this.priority = provider.getPriority();
        this.createdAt = provider.getCreatedAt();
        this.updatedAt = provider.getUpdatedAt();
        this.url = provider.getUrl();
        this.isDefault = provider.isDefault();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if(id == null) {
            id = "";
        }
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(name == null) {
            name = "";
        }
        this.name = name;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        if(priority == null) {
            priority = "";
        }
        this.priority = priority;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        if(createdAt == null) {
            createdAt = "";
        }
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        if(updatedAt == null) {
            updatedAt = "";
        }
        this.updatedAt = updatedAt;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        if(url == null) {
            url = "";
        }
        this.url = url;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }
}

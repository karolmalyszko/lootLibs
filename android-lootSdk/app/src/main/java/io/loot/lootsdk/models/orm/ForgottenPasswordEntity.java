package io.loot.lootsdk.models.orm;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class ForgottenPasswordEntity {

    @PrimaryKey
    private long entityId;
    private String email;

    public long getEntityId() {
        return this.entityId;
    }
    public void setEntityId(long entityId) {
        this.entityId = entityId;
    }
    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}

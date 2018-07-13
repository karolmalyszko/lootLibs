package io.loot.lootsdk.models.orm;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "ServerInfo")
public class ServerInfoEntity {
    @PrimaryKey
    private long entityId;
    private String serverDate;

    public long getEntityId() {
        return this.entityId;
    }
    public void setEntityId(long entityId) {
        this.entityId = entityId;
    }
    public String getServerDate() {
        return this.serverDate;
    }
    public void setServerDate(String serverDate) {
        this.serverDate = serverDate;
    }
}

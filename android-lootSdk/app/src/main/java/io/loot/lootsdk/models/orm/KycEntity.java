package io.loot.lootsdk.models.orm;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import io.loot.lootsdk.models.data.userinfo.Kyc;

@Entity(tableName = "Kyc")
public class KycEntity {

    @PrimaryKey
    private long entityId;
    private String status;
    private String reason;
    private String details;
    private int attempts;

    public Kyc parseToDataObject() {
        Kyc kyc = new Kyc();

        kyc.setAttempts(attempts);
        kyc.setDetails(details);
        kyc.setReason(reason);
        kyc.setStatus(status);

        return kyc;
    }

    public long getEntityId() {
        return this.entityId;
    }

    public void setEntityId(long entityId) {
        this.entityId = entityId;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return this.reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getDetails() {
        return this.details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getAttempts() {
        return this.attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    public static KycEntity parseToEntity(Kyc kyc) {
        KycEntity kycEntity = new KycEntity();

        kycEntity.setAttempts(kyc.getAttempts());
        kycEntity.setDetails(kyc.getDetails());
        kycEntity.setReason(kyc.getReason());
        kycEntity.setStatus(kyc.getStatus());

        return kycEntity;
    }

}

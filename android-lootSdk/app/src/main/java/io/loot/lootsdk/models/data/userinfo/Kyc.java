package io.loot.lootsdk.models.data.userinfo;


import java.io.Serializable;

public class Kyc implements Serializable {
    private String status;
    private String reason;
    private String details;
    private int attempts;

    public Kyc() {
        this.status = "";
        this.reason = "";
        this.details = "";
        this.attempts = 0;
    }

    public Kyc(Kyc kyc) {
        this.status = kyc.getStatus();
        this.reason = kyc.getReason();
        this.details = kyc.getDetails();
        this.attempts = kyc.getAttempts();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if (status == null) {
            status = "";
        }
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        if (reason == null) {
            reason = "";
        }
        this.reason = reason;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        if (details == null) {
            details = "";
        }
        this.details = details;
    }

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }
}

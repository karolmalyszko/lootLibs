package io.loot.lootsdk.models.data.payments;

public class ManualDetailsPayment {
    private String id;
    private Boolean requires2fa;

    public ManualDetailsPayment() {
        this.id = "";
        this.requires2fa = false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (id == null) {
            id = "";
        }
        this.id = id;
    }

    public Boolean getRequires2fa() {
        return requires2fa;
    }

    public void setRequires2fa(Boolean requires2fa) {
        if (requires2fa == null) {
            requires2fa = false;
        }
        this.requires2fa = requires2fa;
    }
}

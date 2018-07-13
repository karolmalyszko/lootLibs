package io.loot.lootsdk.models.data.topUp;

public class TopUpResult {

    String redirectUrl;
    boolean required3ds;

    public TopUpResult() {
        this.redirectUrl = "";
        this.required3ds = false;
    }

    public TopUpResult(TopUpResult topUpResult) {
        this.redirectUrl = topUpResult.getRedirectUrl();
        this.required3ds = topUpResult.isRequired3ds();
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        if (redirectUrl == null) {
            redirectUrl = "";
        }
        this.redirectUrl = redirectUrl;
    }

    public boolean isRequired3ds() {
        return required3ds;
    }

    public void setRequired3ds(boolean required3ds) {
        this.required3ds = required3ds;
    }
}

package io.loot.lootsdk.models.data.userinfo;

public class Session {

    private String authorizationToken = "";
    private String email = "";
    private String intercomHash = "";
    private String onBoardingToken = "";
    private boolean emailVerified = false;
    private String waitingListToken = "";
    private String userId;

    public String getAuthorizationToken() {
        return authorizationToken;
    }

    public void setAuthorizationToken(String authorizationToken) {
        this.authorizationToken = authorizationToken;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIntercomHash() {
        return intercomHash;
    }

    public void setIntercomHash(String intercomHash) {
        this.intercomHash = intercomHash;
    }

    public String getOnBoardingToken() {
        return onBoardingToken;
    }

    public void setOnBoardingToken(String onBoardingToken) {
        this.onBoardingToken = onBoardingToken;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getWaitingListToken() {
        return waitingListToken;
    }

    public void setWaitingListToken(String waitingListToken) {
        this.waitingListToken = waitingListToken;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

package io.loot.lootsdk.models.orm;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class SessionEntity {
    @Id
    private long entityId;
    private String authorizationToken = "";
    private String email = "";
    private String intercomHash = "";
    private String onBoardingToken = "";
    private boolean emailVerified = false;
    private String waitingListToken = "";
    private String userId;
    @Generated(hash = 878773674)
    public SessionEntity(long entityId, String authorizationToken, String email,
            String intercomHash, String onBoardingToken, boolean emailVerified,
            String waitingListToken, String userId) {
        this.entityId = entityId;
        this.authorizationToken = authorizationToken;
        this.email = email;
        this.intercomHash = intercomHash;
        this.onBoardingToken = onBoardingToken;
        this.emailVerified = emailVerified;
        this.waitingListToken = waitingListToken;
        this.userId = userId;
    }
    @Generated(hash = 1440899673)
    public SessionEntity() {
    }
    public long getEntityId() {
        return this.entityId;
    }
    public void setEntityId(long entityId) {
        this.entityId = entityId;
    }
    public String getAuthorizationToken() {
        return this.authorizationToken;
    }
    public void setAuthorizationToken(String authorizationToken) {
        this.authorizationToken = authorizationToken;
    }
    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getIntercomHash() {
        return this.intercomHash;
    }
    public void setIntercomHash(String intercomHash) {
        this.intercomHash = intercomHash;
    }
    public String getOnBoardingToken() {
        return this.onBoardingToken;
    }
    public void setOnBoardingToken(String onBoardingToken) {
        this.onBoardingToken = onBoardingToken;
    }
    public boolean getEmailVerified() {
        return this.emailVerified;
    }
    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }
    public String getWaitingListToken() {
        return this.waitingListToken;
    }
    public void setWaitingListToken(String waitingListToken) {
        this.waitingListToken = waitingListToken;
    }
    public String getUserId() {
        return this.userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

}

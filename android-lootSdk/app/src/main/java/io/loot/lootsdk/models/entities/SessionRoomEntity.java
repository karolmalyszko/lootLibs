package io.loot.lootsdk.models.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import io.loot.lootsdk.models.data.userinfo.Session;

@Entity(tableName = "Session")
public class SessionRoomEntity {

    private String authorizationToken = "";
    private String email = "";
    private String intercomHash = "";
    private String onBoardingToken = "";
    private boolean emailVerified = false;
    private String waitingListToken = "";

    @PrimaryKey
    @NonNull
    private String userId = "0";

    public SessionRoomEntity() {
    }

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

    @NonNull
    public String getUserId() {
        return userId;
    }

    public void setUserId(@NonNull String userId) {
        this.userId = userId;
    }

    public static Session parseToDataObject(SessionRoomEntity entity) {
        Session session = new Session();

        session.setAuthorizationToken(entity.getAuthorizationToken());
        session.setEmail(entity.getEmail());
        session.setEmailVerified(entity.isEmailVerified());
        session.setIntercomHash(entity.getIntercomHash());
        session.setUserId(entity.getUserId());
        session.setWaitingListToken(entity.getWaitingListToken());

        return session;
    }
}

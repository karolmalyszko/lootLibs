package io.loot.lootsdk.models.networking.signup;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.loot.lootsdk.models.entities.SessionRoomEntity;
import io.loot.lootsdk.models.orm.SessionEntity;

public class ConfirmDataResponse implements Serializable {

    @SerializedName("email")
    private String email;
    @SerializedName("token")
    private String token;
    @SerializedName("email_verified")
    private boolean emailVerified;
    @SerializedName("verification_token")
    private String verificationToken;
    @SerializedName("intercom_hash")
    private String intercomHash;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getVerificationToken() {
        return verificationToken;
    }

    public void setVerificationToken(String verificationToken) {
        this.verificationToken = verificationToken;
    }

    public String getIntercomHash() {
        return intercomHash;
    }

    public void setIntercomHash(String intercomHash) {
        this.intercomHash = intercomHash;
    }

    public static SessionRoomEntity parseToEntityObject(ConfirmDataResponse confirmDataResponse) {
        if(confirmDataResponse == null) {
            confirmDataResponse = new ConfirmDataResponse();
        }

        SessionRoomEntity session = new SessionRoomEntity();
        session.setEmail(confirmDataResponse.getEmail());
        session.setEmailVerified(confirmDataResponse.getEmailVerified());
        session.setIntercomHash(confirmDataResponse.getIntercomHash());
        session.setAuthorizationToken(confirmDataResponse.getToken());

        return session;
    }

}

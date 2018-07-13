package io.loot.lootsdk.listeners;

public interface GenericFailListener {

    void onSessionExpired();
    void onFailure(String reason);
    void onUserBlocked();
    void onManualVerificationRequired();

    void onManualVerificationFailed();
    void onUserRejected();
}

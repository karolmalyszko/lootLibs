package io.loot.lootsdk.listeners.user;

import io.loot.lootsdk.listeners.GenericFailListener;

public interface ContactVerificationListener extends GenericFailListener {
    void onContactVerificationSuccess();
    void onContactVerificationError(String error);
}

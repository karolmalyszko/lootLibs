package io.loot.lootsdk.listeners.cards;

import io.loot.lootsdk.listeners.GenericFailListener;

public interface ActivationVerificationListener extends GenericFailListener {
    void onActivationVerified(String pinCode);
    void onActivationVerificationError(String error);
}

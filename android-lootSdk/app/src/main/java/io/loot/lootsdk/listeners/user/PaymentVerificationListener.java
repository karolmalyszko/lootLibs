package io.loot.lootsdk.listeners.user;

import io.loot.lootsdk.listeners.GenericFailListener;

public interface PaymentVerificationListener extends GenericFailListener {
    void onPaymentVerificationSuccess();
    void onPaymentVerificationError(String error);
}

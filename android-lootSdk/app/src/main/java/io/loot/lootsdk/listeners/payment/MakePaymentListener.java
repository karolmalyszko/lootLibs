package io.loot.lootsdk.listeners.payment;

import io.loot.lootsdk.listeners.GenericFailListener;

public interface MakePaymentListener extends GenericFailListener {

    void onPaymentMadeSuccessful();
    void onPaymentRequire2FA(String paymentId);
    void onPaymentError(String error);

}

package io.loot.lootsdk.listeners.signup;

import io.loot.lootsdk.listeners.GenericFailListener;

public interface KYCResultListener extends GenericFailListener {

    void onResultReceived(String status);

}

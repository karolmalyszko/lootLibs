package io.loot.lootsdk.listeners.signup;

import io.loot.lootsdk.listeners.GenericFailListener;
import io.loot.lootsdk.models.data.signup.FailedScansContainer;

public interface FailedScansListener extends GenericFailListener {

    void onScansReceived(FailedScansContainer container);
    void onError(String error);

}

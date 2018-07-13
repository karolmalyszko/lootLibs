package io.loot.lootsdk.listeners.signup;

import io.loot.lootsdk.listeners.GenericFailListener;
import okhttp3.ResponseBody;

public interface GetScannedImageListener extends GenericFailListener {

    void onScanAvailable(ResponseBody body);
    void onScanNotAvailbe();
}

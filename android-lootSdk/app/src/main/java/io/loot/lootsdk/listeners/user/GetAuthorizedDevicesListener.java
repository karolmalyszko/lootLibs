package io.loot.lootsdk.listeners.user;

import java.util.List;

import io.loot.lootsdk.listeners.GenericFailListener;
import io.loot.lootsdk.models.data.authToken.AuthorizedDevice;

public interface GetAuthorizedDevicesListener extends GenericFailListener {

    void onGetCachedDevices(List<AuthorizedDevice> authorizedDevices);
    void onGetDevices(List<AuthorizedDevice> authorizedDevices);
    void onGetDevicesError(String error);

}

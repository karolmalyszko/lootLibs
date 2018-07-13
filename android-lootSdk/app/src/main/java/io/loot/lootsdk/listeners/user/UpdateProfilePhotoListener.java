package io.loot.lootsdk.listeners.user;

import io.loot.lootsdk.listeners.GenericFailListener;

public interface UpdateProfilePhotoListener extends GenericFailListener {

    void onPhotoUpdated(String photoUrl);
    void onUpdatePhotoError(String error);

}

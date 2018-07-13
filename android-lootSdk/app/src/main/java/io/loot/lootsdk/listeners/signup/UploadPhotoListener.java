package io.loot.lootsdk.listeners.signup;

import io.loot.lootsdk.listeners.GenericFailListener;

public interface UploadPhotoListener extends GenericFailListener {

    void onImageUploaded();
    void onError(String error);

}

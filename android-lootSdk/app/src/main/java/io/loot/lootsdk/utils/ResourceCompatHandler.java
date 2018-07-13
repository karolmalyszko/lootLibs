package io.loot.lootsdk.utils;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import io.loot.lootsdk.listeners.GenericFailListener;
import io.loot.lootsdk.models.Resource;

import static io.loot.lootsdk.models.networking.ErrorExtractor.UNEXPECTED_ERROR;

/**
 * Deprecate since version 2.1.0 due to migration to Android Architecture
 * Components in composition between Loot App and Loot SDK.
 * Use LiveData instead.
 */
@Deprecated
public abstract class ResourceCompatHandler<T> {
    private LiveData<Resource<T>> liveData;
    private GenericFailListener listener;
    private Resource<T> resource;

    public ResourceCompatHandler(LiveData<Resource<T>> liveData, GenericFailListener listener) {
        this.liveData = liveData;
        this.listener = listener;
    }

    public void handle() {
        Observer<Resource<T>> observer = new Observer<Resource<T>>() {
            @Override
            public void onChanged(@Nullable Resource<T> resource) {
                if (resource == null) {
                    onError(UNEXPECTED_ERROR);
                    liveData.removeObserver(this);
                    return;
                }

                boolean wasHandled = BackwardCompatibilityHandler.handleListener(listener, resource);
                if (wasHandled) {
                    liveData.removeObserver(this);
                    return;
                }

                if (!resource.isSuccessful()) {
                    ResourceCompatHandler.this.resource = resource;
                    onError(resource.getErrorMessage());
                    onDetailedError(resource.getErrorMessage(), resource.getResponseCode());
                    liveData.removeObserver(this);
                }

                if (resource.isSuccessful() && resource.isCached()) {
                    ResourceCompatHandler.this.resource = resource;
                    onCachedDataReceived(resource.getData());
                } else if (resource.isSuccessful() && resource.isLive()) {
                    ResourceCompatHandler.this.resource = resource;
                    onLiveDataReceived(resource.getData());
                    liveData.removeObserver(this);
                }
            }
        };

        liveData.observeForever(observer);
    }

    public abstract void onError(String errorMessage);

    public abstract void onCachedDataReceived(T data);

    public abstract void onLiveDataReceived(T data);

    public void onDetailedError(String errorMessage, int responseCode) {

    }

    public Resource<T> getCurrentResource() {
        return resource;
    }
}

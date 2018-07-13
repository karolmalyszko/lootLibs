package io.loot.lootsdk.utils;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import io.loot.lootsdk.listeners.GenericFailListener;
import io.loot.lootsdk.models.ActionResult;

/**
 * Deprecate since version 2.1.0 due to migration to Android Architecture
 * Components in composition between Loot App and Loot SDK.
 * Use LiveData instead.
 */
@Deprecated
public abstract class ActionCompatHandler {
    private LiveData<ActionResult> liveData;
    private GenericFailListener listener;

    public ActionCompatHandler(LiveData<ActionResult> liveData, GenericFailListener listener) {
        this.liveData = liveData;
        this.listener = listener;
    }

    public void handle() {
        Observer<ActionResult> observer = new Observer<ActionResult>() {
            @Override
            public void onChanged(@Nullable ActionResult actionResult) {
                if (actionResult == null) {
                    return;
                }

                boolean wasHandled = BackwardCompatibilityHandler.handleListener(listener, actionResult);
                if (wasHandled) {
                    liveData.removeObserver(this);
                    return;
                }

                if (actionResult.isSuccess()) {
                    onSuccess();
                    liveData.removeObserver(this);
                } else if (actionResult.isError()) {
                    onError(actionResult.getErrorMessage());
                    liveData.removeObserver(this);
                }

            }
        };

        liveData.observeForever(observer);
    }

    public abstract void onError(String errorMessage);

    public abstract void onSuccess();
}

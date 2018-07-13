package io.loot.lootsdk.utils;

import io.loot.lootsdk.listeners.GenericFailListener;
import io.loot.lootsdk.models.ActionResult;
import io.loot.lootsdk.models.Resource;

import static io.loot.lootsdk.models.networking.ErrorExtractor.CANNOT_PROCEED;
import static io.loot.lootsdk.models.networking.ErrorExtractor.FAILURE;
import static io.loot.lootsdk.models.networking.ErrorExtractor.MANUAL_VERIFICATION_REJECTED;
import static io.loot.lootsdk.models.networking.ErrorExtractor.MANUAL_VERIFICATION_REQUIRED;
import static io.loot.lootsdk.models.networking.ErrorExtractor.REJECTED;
import static io.loot.lootsdk.models.networking.ErrorExtractor.SESSION_EXPIRED;

/**
 * Deprecate since version 2.1.0 due to migration to Android Architecture
 * Components in composition between Loot App and Loot SDK.
 * Use LiveData instead.
 */
@Deprecated
public class BackwardCompatibilityHandler {

    public static boolean handleListener(GenericFailListener listener, String errorMesage) {
        if (errorMesage == null || errorMesage.isEmpty()) {
            return false;
        }

        if (errorMesage.equals(SESSION_EXPIRED)) {
            listener.onSessionExpired();
            return true;
        }

        if (errorMesage.startsWith(CANNOT_PROCEED)) {
            listener.onUserBlocked();
            return true;
        }

        if (errorMesage.startsWith(MANUAL_VERIFICATION_REQUIRED)) {
            listener.onManualVerificationRequired();
            return true;
        }

        if (errorMesage.startsWith(MANUAL_VERIFICATION_REJECTED)) {
            listener.onManualVerificationFailed();
            return true;
        }

        if (errorMesage.startsWith(REJECTED)) {
            listener.onUserRejected();
            return true;
        }

        if (errorMesage.equals(FAILURE)) {
            listener.onFailure(FAILURE);
            return true;
        }

        return false;
    }

    public static boolean handleListener(GenericFailListener listener, Resource<?> resource) {
        if (resource == null || resource.getRawError() == null|| resource.getRawError().isEmpty()) {
            return false;
        }

       return handleListener(listener, resource.getErrorMessage());
    }

    public static boolean handleListener(GenericFailListener listener, ActionResult result) {
        if (result == null || result.getErrorMessage() == null || result.getErrorMessage().isEmpty()) {
            return false;
        }

        return handleListener(listener, result.getErrorMessage());
    }

}

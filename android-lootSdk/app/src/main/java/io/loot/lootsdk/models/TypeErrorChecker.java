package io.loot.lootsdk.models;

import static io.loot.lootsdk.models.networking.ErrorExtractor.CANNOT_PROCEED;
import static io.loot.lootsdk.models.networking.ErrorExtractor.SESSION_EXPIRED;
import static io.loot.lootsdk.models.networking.ErrorExtractor.SIGNATURE_HAS_EXPIRED;
import static io.loot.lootsdk.models.networking.ErrorExtractor.TOKEN_EXPIRED;

public class TypeErrorChecker {

    public static boolean isSessionExpired(int code, String stringResponse) {
        String error = ErrorParser.getErrorMsg(code, stringResponse);
        return error.toUpperCase().equals(SESSION_EXPIRED) || error.toUpperCase().equals(TOKEN_EXPIRED) || error.toUpperCase().equals(SIGNATURE_HAS_EXPIRED);
    }

    public static boolean isUserBlocked(int code, String stringResponse) {
        String error = ErrorParser.getErrorMsg(code, stringResponse);
        return error.toUpperCase().startsWith(CANNOT_PROCEED);
    }
}

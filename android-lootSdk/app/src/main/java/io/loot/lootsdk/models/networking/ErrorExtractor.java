package io.loot.lootsdk.models.networking;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.loot.lootsdk.models.ErrorParser;
import io.loot.lootsdk.models.TypeErrorChecker;

public class ErrorExtractor extends TypeErrorChecker {
    public static final String UNEXPECTED_ERROR = "UNEXPECTED_ERROR";
    public static final String FAILURE = "FAILURE";
    public static final String SESSION_EXPIRED = "SESSION_EXPIRED";
    public static final String CANNOT_PROCEED = "CANNOT_PROCEED";
    public static final String ACCOUNT_BLOCKED = "ACCOUNT_BLOCKED";
    public static final String TOKEN_EXPIRED = "TOKEN_EXPIRED";
    public static final String SIGNATURE_HAS_EXPIRED = "SIGNATURE_HAS_EXPIRED";
    public static final String NO_INTERNET_CONNECTION = "NO_INTERNET_CONNECTION";
    public static final String NOT_FOUND = "NOT_FOUND";
    public static final String MANUAL_VERIFICATION_REQUIRED = "MANUAL_VERIFICATION_REQUIRED";
    public static final String MANUAL_VERIFICATION_REJECTED = "MANUAL_VERIFICATION_REJECTED";
    public static final String REJECTED = "REJECTED";

    public static String extract(int code, String stringResponse) {
        if (stringResponse == null) {
            return UNEXPECTED_ERROR;
        }
        if (!stringResponse.isEmpty() && stringResponse.equals(NO_INTERNET_CONNECTION)) {
            return NO_INTERNET_CONNECTION;
        }
        if (code == 500) {
            return UNEXPECTED_ERROR;
        }

        String error = "";
        Gson gson = new GsonBuilder().create();
        try {
            error = ErrorParser.getErrorMsg(code, stringResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return (error != null && !error.isEmpty()) ? error : UNEXPECTED_ERROR;
    }

    public static boolean isNotFound(int code, String stringResponse) {
        String error = ErrorParser.getErrorMsg(code, stringResponse);
        return error.toUpperCase().equals(NOT_FOUND);
    }

}

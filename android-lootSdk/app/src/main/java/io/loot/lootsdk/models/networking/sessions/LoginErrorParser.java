package io.loot.lootsdk.models.networking.sessions;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;

import io.loot.lootsdk.R;
import io.loot.lootsdk.models.TypeErrorChecker;
import io.loot.lootsdk.models.networking.ErrorExtractor;

public class LoginErrorParser extends TypeErrorChecker {

    public static String parse(int errorCode, String errorString, Context context) {
        if (errorCode == 500 || context == null) {
            return ErrorExtractor.UNEXPECTED_ERROR;
        }

        LoginErrorResponse error = new LoginErrorResponse();
        Gson gson = new GsonBuilder().create();
        try {
            error = gson.fromJson(errorString, LoginErrorResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (error == null) {
            return context.getString(R.string.unexpected_error);
        }

        if (error.getType() != null && !error.getType().isEmpty()) {
            if (error.getType().equals("user_not_found") || error.getType().equals("not_found")) {
                return context.getString(R.string.email_or_password_is_incorrect);
            } else if (error.getType().equals("password_invalid")) {
                return "INVALID_PASSWORD";
            }
        }

        HashMap<String, String> errors = error.getErrorsHashMap();
        if (errors != null) {
            for (String msg : errors.values()) {
                return msg;
            }
        }
        return context.getString(R.string.unexpected_error);
    }

    private static boolean isTokenError(LoginErrorResponse error) {
        if (error == null || error.getType() == null) {
            return false;
        }
        switch (error.getType()) {
            case "invalid_token":
                return true;
            case "token_expired":
                return true;
            default:
                return false;
        }
    }

    public static String parseEnumError(String message, Context context) {
        switch (message) {
            case ErrorExtractor.NOT_FOUND:
                return context.getString(R.string.sms_verification_not_found);
            case "INVALID_LAST_FINISHED_STEP":
                return context.getString(R.string.sms_verification_invalid_last_finished_step);
            default:
                return message;
        }
    }
}

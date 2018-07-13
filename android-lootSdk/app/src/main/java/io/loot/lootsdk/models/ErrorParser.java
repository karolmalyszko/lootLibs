package io.loot.lootsdk.models;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

import io.loot.lootsdk.R;

import static io.loot.lootsdk.models.networking.ErrorExtractor.SESSION_EXPIRED;
import static io.loot.lootsdk.models.networking.ErrorExtractor.UNEXPECTED_ERROR;

public class ErrorParser {

    public static String getErrorMsg(int code, String stringResponse) {
        if (code == 500) {
            return UNEXPECTED_ERROR;
        }
        ErrorResponse error = new ErrorResponse();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(ErrorResponse.class, new ErrorResponseDeserilizer())
                .create();
        try {
            error = gson.fromJson(stringResponse, ErrorResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(error != null) {
            if (isTokenError(error)) {
                return SESSION_EXPIRED;
            }

            if (error.getErrorString() != null && !error.getErrorString().isEmpty()) {
                return error.getErrorString();
            }


            HashMap<String, ArrayList<String>> errors = error.getErrorsHashMap();

            if (errors != null) {
                for (ArrayList<String> ar : errors.values()) {
                    for (String msg : ar) {
                        return msg;
                    }
                }
            }
        }

        if (error != null && error.getType() != null && !error.getType().isEmpty()) {
            if (error.getType().equals("user_not_found") || error.getType().equals("not_found") || error.getType().equals("password_invalid")) {
                return "INVALID_PASSWORD";
            }
        }

        return UNEXPECTED_ERROR;
    }

    private static boolean isTokenError(ErrorResponse error) {
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
            case "INVALID_LAST_FINISHED_STEP":
                return context.getString(R.string.sms_verification_invalid_last_finished_step);
            default:
                return "";
        }
    }

    public static class ErrorResponseDeserilizer implements JsonDeserializer<ErrorResponse> {
        @Override
        public ErrorResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            ErrorResponse response = new Gson().fromJson(json, ErrorResponse.class);
            JsonObject jsonObject = json.getAsJsonObject();
            if (jsonObject.has("table")) {
                jsonObject = jsonObject.getAsJsonObject("table");
            }
            if (jsonObject.has("error")) {
                JsonElement elem = jsonObject.get("error");
                if (elem != null && !elem.isJsonNull()) {
                    if(elem.isJsonObject()) {
                        HashMap<String, ArrayList<String>> values = new Gson().fromJson(elem, new TypeToken<HashMap<String, ArrayList<String>>>() {}.getType());
                        response.setErrorsHashMap(values);
                    }
                    else {
                        response.setErrorString(elem.getAsString());
                        JsonElement typeElem = jsonObject.get("type");
                        if(typeElem != null) {
                            response.setType(typeElem.getAsString());
                        }
                    }
                }
            }
            return response;
        }
    }
}

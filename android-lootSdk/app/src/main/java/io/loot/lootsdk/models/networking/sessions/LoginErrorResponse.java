package io.loot.lootsdk.models.networking.sessions;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;

import lombok.Data;

public @Data
class LoginErrorResponse {
    @SerializedName("codes")
    private ArrayList<Integer> codes;

    @SerializedName("type")
    private String type;

    @SerializedName("errors")
    private HashMap<String, String> errorsHashMap;
}

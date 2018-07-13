package io.loot.lootsdk.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;

import lombok.Data;

public @Data class ErrorResponse {

    @SerializedName("codes")
    private  ArrayList<Integer> codes;

    @SerializedName("type")
    private String type;

    private String errorString;

    private HashMap<String, ArrayList<String>> errorsHashMap;

}

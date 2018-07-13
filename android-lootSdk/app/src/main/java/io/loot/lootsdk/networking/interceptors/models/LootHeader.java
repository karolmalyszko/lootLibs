package io.loot.lootsdk.networking.interceptors.models;

import lombok.Data;

@Data
public class LootHeader {

    private String authUsername = "";
    private String authPassword = "";
    private String authToken = "";
    private String onboardingToken = "";
    private String email = "";
    private String analyticsDataBase64 = "";

}

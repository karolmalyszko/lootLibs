package io.loot.lootsdk.exceptions;

public class NotInitializedException extends RuntimeException {

    @Override
    public String getMessage() {
        return "Before any call you have to initialize LootSDK!";
    }
}

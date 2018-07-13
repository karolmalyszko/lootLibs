package io.loot.lootsdk.exceptions;

public class NullPointerListenerException extends RuntimeException {

    @Override
    public String getMessage() {
        return "Listener cannot be null!";
    }
}

package io.loot.lootsdk.exceptions;

public class IncorrectPinException extends Exception {
    @Override
    public String getMessage() {
        return "Incorrect PIN";
    }
}

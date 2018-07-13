package io.loot.lootsdk.exceptions;

public class NoPinOnDeviceException extends RuntimeException {
    @Override
    public String getMessage() {
        return "No PIN or Authorization Token found on device!";
    }
}

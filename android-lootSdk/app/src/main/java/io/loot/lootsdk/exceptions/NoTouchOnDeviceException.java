package io.loot.lootsdk.exceptions;

public class NoTouchOnDeviceException extends RuntimeException {
    @Override
    public String getMessage() {
        return "No Touch Authorization Token found on device!";
    }
}

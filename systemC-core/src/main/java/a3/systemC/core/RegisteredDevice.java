package a3.systemC.core;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Weinan Qiu
 * @since 1.0.0
 */
public class RegisteredDevice {

    private final String deviceId;
    private final String description;
    private boolean online = false;
    private ScheduledExecutorService scheduledExecutorService;

    public RegisteredDevice(String deviceId, String description) {
        this.deviceId = deviceId;
        this.description = description;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getDescription() {
        return description;
    }

    private void scheduleNewInvalidation(Runnable invalidatedCallback) {
        this.scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        this.scheduledExecutorService.schedule(invalidatedCallback, 5, TimeUnit.SECONDS);
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online, Runnable invalidatedCallback) {
        this.online = online;
        if (this.scheduledExecutorService != null) {
            this.scheduledExecutorService.shutdownNow();
        }

        if (this.online) {
            this.scheduleNewInvalidation(invalidatedCallback);
        }
    }
}

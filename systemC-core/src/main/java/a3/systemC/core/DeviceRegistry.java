package a3.systemC.core;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Weinan Qiu
 * @since 1.0.0
 */
public class DeviceRegistry {

    private static final Map<String, RegisteredDevice> registry = new ConcurrentHashMap<>();

    synchronized void registerDevice(RegisteredDevice newDevice) {
        registry.put(newDevice.getDeviceId(), newDevice);
    }

    synchronized RegisteredDevice getDevice(String id) {
        return registry.get(id);
    }

    synchronized RegisteredDevice removeDevice(String id) {
        return registry.remove(id);
    }
}

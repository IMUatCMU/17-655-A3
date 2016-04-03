package a3.systemC.core;

import a3.message.Message;
import a3.monitor.MonitorMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * {@link MonitorMessageHandler} implementation for registration
 *
 * @author Weinan Qiu
 * @since 1.0.0
 */
public class RegistrationHandler implements MonitorMessageHandler {

    @Autowired
    private DeviceRegistry registry;

    @Autowired
    private HeartBeatConsole heartBeatConsole;

    @Override
    public boolean canHandleMessageWithId(int id) {
        return 1000 == id;
    }

    @Override
    public void handleMessage(Message message, Object context) {
        String deviceId = message.GetMessage().split(";")[0];
        String description = message.GetMessage().split(";")[1];

        RegisteredDevice device = new RegisteredDevice(deviceId, description);
        registry.registerDevice(device);
        heartBeatConsole.writeMessage("[√] " + device.getDeviceId() + " (" + device.getDescription() + ") registered");
    }
}

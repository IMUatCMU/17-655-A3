package a3.systemC.core;

import a3.message.Message;
import a3.monitor.MonitorMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * {@link MonitorMessageHandler} implementation for heartbeats
 *
 * @author Weinan Qiu
 * @since 1.0.0
 */
public class HeartbeatHandler implements MonitorMessageHandler {

    @Autowired
    private DeviceRegistry registry;

    @Autowired
    private HeartBeatConsole heartBeatConsole;

    @Override
    public boolean canHandleMessageWithId(int id) {
        return 1001 == id;
    }

    @Override
    public void handleMessage(Message message, Object context) {
        String id = message.GetMessage();
        RegisteredDevice device = registry.getDevice(id);

        if (device == null)
            return;

        if (!device.isOnline()) {
            heartBeatConsole.writeMessage("[√] " + device.getDeviceId() + " went online");
        }

        device.setOnline(true, () -> {
            heartBeatConsole.writeMessage("[x] " + device.getDeviceId() + " went offline");
        });
    }
}

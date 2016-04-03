package a3.systemC.core;

import a3.message.Message;
import a3.monitor.MonitorMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * {@link MonitorMessageHandler} implementation for unregistration call.
 *
 * @author Weinan Qiu
 * @since 1.0.0
 */
public class UnregisterationHandler implements MonitorMessageHandler {

    @Autowired
    private DeviceRegistry registry;

    @Autowired
    private HeartBeatConsole heartBeatConsole;

    @Override
    public boolean canHandleMessageWithId(int id) {
        return 1002 == id;
    }

    @Override
    public void handleMessage(Message message, Object context) {
        RegisteredDevice device = registry.removeDevice(message.GetMessage());
        if (device == null)
            return;

        device.setOnline(false, () -> {});
        heartBeatConsole.writeMessage("[x] " + device.getDeviceId() + " unregistered");
    }
}

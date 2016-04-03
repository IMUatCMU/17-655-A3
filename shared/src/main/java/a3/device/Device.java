package a3.device;

import a3.message.Message;
import a3.message.MessageManagerInterface;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.DisposableBean;

/**
 * Superclass for all controllers and sensors to provide registration and heartbeat capabilities.
 *
 * @author Weinan Qiu
 * @since 1.0.0
 */
public abstract class Device implements DisposableBean {

    protected abstract MessageManagerInterface messageManager();

    protected abstract String deviceId();

    protected abstract String description();

    private ScheduledExecutorService s = Executors.newSingleThreadScheduledExecutor();

    protected void registerAndStartHeartbeat() {
        Message registration = new Message(1000, String.format("%s;%s", deviceId(), description()));

        try {
            messageManager().SendMessage(registration);
        } catch (Exception ex) {
            System.out.println("Failed to register device " + deviceId());
            return;
        }

        s.scheduleAtFixedRate(() -> {
            Message heartbeat = new Message(1001, deviceId());
            try {
                messageManager().SendMessage(heartbeat);
            } catch (Exception ex) {
                System.out.println("Failed to send heartbeat for " + deviceId());
            }
        }, 1, 1, TimeUnit.SECONDS);
    }

    @Override
    public void destroy() throws Exception {
        Message unregisteration = new Message(1002, deviceId());

        try {
            messageManager().SendMessage(unregisteration);
        } catch (Exception ex) {
            System.out.println("Failed to unregister device " + deviceId());
            return;
        }

        s.shutdown();
    }
}

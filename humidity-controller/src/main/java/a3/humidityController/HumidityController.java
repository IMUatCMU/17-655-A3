package a3.humidityController;

import a3.device.Device;
import a3.message.Message;
import a3.message.MessageManagerInterface;
import a3.message.MessageQueue;
import a3.monitor.MessageWindow;
import a3.monitor.MonitorMessageHandler;
import a3.monitor.MonitorQuitSignal;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * @author Weinan Qiu
 * @since 1.0.0
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"a3"})
@ImportResource("classpath:config.xml")
public class HumidityController extends Device implements InitializingBean {

    private static final String _UUID = UUID.randomUUID().toString();

    @Autowired
    private MessageWindow messageWindow;

    @Autowired
    private MessageManagerInterface messageManager;

    @Autowired
    List<MonitorMessageHandler> messageHandlers;

    @Override
    protected MessageManagerInterface messageManager() {
        return this.messageManager;
    }

    @Override
    protected String deviceId() {
        return "humidity-controller@" + _UUID;
    }

    @Override
    protected String description() {
        return "Humidity Controller";
    }

    private void start() {
        super.registerAndStartHeartbeat();
        displayStartInformation();

        boolean done = false;
        while (!done) {
            MessageQueue messageQueue;
            try {
                messageQueue = messageManager.GetMessageQueue();
            } catch (Exception ex) {
                messageWindow.WriteMessage("Error getting message queue::" + ex);
                break;
            }

            try {
                int size = messageQueue.GetSize();
                for (int i = 0; i < size; i++) {
                    Message message = messageQueue.GetMessage();

                    for (MonitorMessageHandler handler : messageHandlers) {
                        if (handler.canHandleMessageWithId(message.GetMessageId())) {
                            handler.handleMessage(message);
                        }
                    }
                }
            } catch (MonitorQuitSignal signal) {
                done = true;
                try {
                    messageManager.UnRegister();
                } catch (Exception ex) {
                    messageWindow.WriteMessage("Error unregistering: " + ex);
                }
                messageWindow.WriteMessage("\n\nSimulation Stopped.");
            }

            try {
                Thread.sleep(2500);
            } catch (Exception e) {
                messageWindow.WriteMessage("Sleep error:: " + e);
            }
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.start();
    }

    private void displayStartInformation() {
        messageWindow.WriteMessage("Registered with the message manager.");
        try {
            messageWindow.WriteMessage("   Participant id: " + messageManager.GetMyId());
            messageWindow.WriteMessage("   Registration Time: " + messageManager.GetRegistrationTime());
        } catch (Exception ex) {
            messageWindow.WriteMessage("Error:: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(HumidityController.class).headless(false).run(args);
    }
}

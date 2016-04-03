package a3.fireSensor;

import a3.device.Device;
import a3.message.Message;
import a3.message.MessageManagerInterface;
import a3.message.MessageQueue;
import a3.monitor.MessageWindow;
import a3.sensor.MessageResponder;
import a3.sensor.SensorQuitSignal;
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
 * Main class for the fire sensor. Delegates message processing to {@link MessageResponder}
 *
 * @author Weinan Qiu
 * @since 1.0.0
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"a3"})
@ImportResource("classpath:config.xml")
public class FireSensor extends Device implements InitializingBean {

    private static final String _UUID = UUID.randomUUID().toString();

    @Autowired
    private MessageWindow messageWindow;

    @Autowired
    private MessageManagerInterface messageManager;

    @Autowired
    private List<MessageResponder> messageResponders;

    @Override
    protected MessageManagerInterface messageManager() {
        return this.messageManager;
    }

    @Override
    protected String deviceId() {
        return "fire-sensor@" + _UUID;
    }

    @Override
    protected String description() {
        return "Fire Sensor";
    }

    private void start() {
        super.registerAndStartHeartbeat();
        displayStartInformation();

        messageWindow.WriteMessage("Initializing Fire Simulation::");
        FireContext context = new FireContext();
        messageWindow.WriteMessage("Beginning Simulation... ");

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

                    for (MessageResponder responder : messageResponders) {
                        if (responder.canRespondToMessageWithId(message.GetMessageId())) {
                            responder.respondToMessage(message, context);
                        }
                    }
                }
            } catch (SensorQuitSignal signal) {
                done = true;
                try {
                    messageManager.UnRegister();
                } catch (Exception ex) {
                    messageWindow.WriteMessage("Error unregistering: " + ex);
                }
                messageWindow.WriteMessage("\n\nSimulation Stopped.");
            }

            displayCurrentStatus(context);
            postStatus(context);

            try {
                Thread.sleep(2500);
            } catch (Exception e) {
                messageWindow.WriteMessage("Sleep error:: " + e);
            }
        }
    }

    private void postStatus(FireContext context) {
        try {
            Message message = new Message(9, String.valueOf(context.getFireLevel()));
            messageManager.SendMessage(message);
        } catch (Exception e) {
            System.out.println("Error Posting Fire Level:: " + e);
        }
    }

    private void displayCurrentStatus(FireContext context) {
        messageWindow.WriteMessage("Fire Level:: " + context.getFireLevel());
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

    @Override
    public void afterPropertiesSet() throws Exception {
        this.start();
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(FireSensor.class).headless(false).run(args);
    }
}

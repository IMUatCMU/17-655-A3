package a3.intrusionSensor;

import a3.message.Message;
import a3.message.MessageManagerInterface;
import a3.message.MessageQueue;
import a3.monitor.MessageWindow;
import a3.sensor.MessageResponder;
import a3.sensor.SensorQuitSignal;
import java.util.List;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import static a3.assist.RandomHelper.getRandomNumber;
import static a3.intrusionSensor.IntrusionContext.*;

/**
 * @author Weinan Qiu
 * @since 1.0.0
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"a3"})
@ImportResource("classpath:config.xml")
public class IntrusionSensor implements InitializingBean {

    @Autowired
    private MessageWindow messageWindow;

    @Autowired
    private MessageManagerInterface messageManager;

    @Autowired
    private List<MessageResponder> messageResponders;

    private void start() {
        displayStartInformation();

        messageWindow.WriteMessage("Initializing Intrusion Simulation::");
        IntrusionContext context = new IntrusionContext();
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

    @Override
    public void afterPropertiesSet() throws Exception {
        this.start();
    }

    private void displayCurrentStatus(IntrusionContext context) {
        messageWindow.WriteMessage(
                String.format("Current Status:: Window - %s  Door - %s  Motion - %s",
                        !context.isWindowArmed() ? "DISARMED" : (context.isWindowStatus() ? "NORMAL" : "BREAK"),
                        !context.isDoorArmed() ? "DISARMED" : (context.isDoorStatus() ? "NORMAL" : "BREAK"),
                        !context.isMotionArmed() ? "DISARMED" : (context.isMotionStatus() ? "NORMAL" : "BREAK")
                ));
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

    private void postStatus(IntrusionContext context) {
        try {
            if (context.isDoorArmed()) {
                Message m = new Message(6, context.isDoorStatus() ? "D0" : "D1");
                messageManager.SendMessage(m);
            }

            if (context.isWindowArmed()) {
                Message m = new Message(6, context.isWindowStatus() ? "W0" : "W1");
                messageManager.SendMessage(m);
            }

            if (context.isMotionArmed()) {
                Message m = new Message(6, context.isMotionStatus() ? "M0" : "M1");
                messageManager.SendMessage(m);
            }
        } catch (Exception e) {
            System.out.println("Error Posting Intrusion Status:: " + e);
        }
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(IntrusionSensor.class).headless(false).run(args);
    }
}

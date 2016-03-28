package a3.tempSensor;

import a3.message.Message;
import a3.message.MessageManagerInterface;
import a3.message.MessageQueue;
import a3.monitor.MessageWindow;
import a3.sensor.MessageResponder;
import a3.sensor.SensorQuitSignal;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import static a3.assist.RandomHelper.coinToss;
import static a3.assist.RandomHelper.getRandomNumber;

/**
 * @author Weinan Qiu
 * @since 1.0.0
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"a3"})
@ImportResource("classpath:config.xml")
public class TemperatureSensor implements InitializingBean {

    @Autowired
    private MessageWindow messageWindow;

    @Autowired
    private MessageManagerInterface messageManager;

    @Autowired
    private List<MessageResponder> messageResponders;

    private void start() {
        displayStartInformation();

        messageWindow.WriteMessage("Initializing Temperature Simulation::");
        Float temp = 50.00f, driftValue;
        if (coinToss()) {
            driftValue = getRandomNumber() * -1.0f;
        } else {
            driftValue = getRandomNumber();
        }
        messageWindow.WriteMessage("   Initial Temperature Set:: " + temp);
        messageWindow.WriteMessage("   Drift Value Set:: " + driftValue);

        messageWindow.WriteMessage("Beginning Simulation... ");
        postTemperature(temp);
        messageWindow.WriteMessage("Current Temperature::  " + temp + " F");

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
                for (int i = 0; i < messageQueue.GetSize(); i++) {
                    Message message = messageQueue.GetMessage();

                    for (MessageResponder responder : messageResponders) {
                        if (responder.canRespondToMessageWithId(message.GetMessageId())) {
                            temp = (Float) responder.respondToMessage(message, Arrays.asList(temp, driftValue));
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

            try {
                Thread.sleep(2500);
            } catch (Exception e) {
                messageWindow.WriteMessage("Sleep error:: " + e);
            }
        }
    }

    private void postTemperature(Float temp) {
        Message msg = new Message(1, String.valueOf(temp));

        try {
            messageManager.SendMessage(msg);
        } catch (Exception e) {
            System.out.println("Error Posting Temperature:: " + e);
        }
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
        new SpringApplicationBuilder(TemperatureSensor.class).headless(false).run(args);
    }
}

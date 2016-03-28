package a3.humiditySensor;

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
public class HumiditySensor implements InitializingBean {

    @Autowired
    private MessageWindow messageWindow;

    @Autowired
    private MessageManagerInterface messageManager;

    @Autowired
    private List<MessageResponder> messageResponders;

    private void start() {
        displayStartInformation();

        messageWindow.WriteMessage("Initializing Humidity Simulation::");
        Float humidity = 100.00f * getRandomNumber(), driftValue;
        if (coinToss()) {
            driftValue = getRandomNumber() * -1.0f;
        } else {
            driftValue = getRandomNumber();
        }
        messageWindow.WriteMessage("   Initial Humidity Set:: " + humidity);
        messageWindow.WriteMessage("   Drift Value Set:: " + driftValue);

        messageWindow.WriteMessage("Beginning Simulation... ");
        postHumidity(humidity);
        messageWindow.WriteMessage("Current Humidity::  " + humidity + " %");

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
                            humidity = (Float) responder.respondToMessage(message, Arrays.asList(humidity, driftValue));
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

    private void displayStartInformation() {
        messageWindow.WriteMessage("Registered with the message manager.");
        try {
            messageWindow.WriteMessage("   Participant id: " + messageManager.GetMyId());
            messageWindow.WriteMessage("   Registration Time: " + messageManager.GetRegistrationTime());
        } catch (Exception ex) {
            messageWindow.WriteMessage("Error:: " + ex.getMessage());
        }
    }

    private void postHumidity(Float humidity) {
        Message msg = new Message(2, String.valueOf(humidity));

        try {
            messageManager.SendMessage(msg);
        } catch (Exception e) {
            System.out.println("Error Posting Humidity:: " + e);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.start();
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(HumiditySensor.class).headless(false).run(args);
    }
}

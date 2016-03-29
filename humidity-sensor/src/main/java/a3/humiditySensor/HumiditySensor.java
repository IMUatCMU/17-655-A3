package a3.humiditySensor;

import a3.device.Device;
import a3.message.Message;
import a3.message.MessageManagerInterface;
import a3.message.MessageQueue;
import a3.monitor.MessageWindow;
import a3.sensor.MessageResponder;
import a3.sensor.SensorQuitSignal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
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
public class HumiditySensor extends Device implements InitializingBean {

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
        return "humidity-sensor@" + _UUID;
    }

    @Override
    protected String description() {
        return "Humidity Sensor";
    }

    private void start() {
        super.registerAndStartHeartbeat();
        displayStartInformation();

        messageWindow.WriteMessage("Initializing Humidity Simulation::");
        HumidityContext context = new HumidityContext();
        context.setCurrentHumidity(100.00f * getRandomNumber());
        if (coinToss()) {
            context.setDriftValue(getRandomNumber() * -1.0f);
        } else {
            context.setDriftValue(getRandomNumber());
        }
        messageWindow.WriteMessage("   Initial Humidity Set:: " + context.getCurrentHumidity());
        messageWindow.WriteMessage("   Drift Value Set:: " + context.getDriftValue());

        messageWindow.WriteMessage("Beginning Simulation... ");
        postHumidity(context.getCurrentHumidity());
        messageWindow.WriteMessage("Current Humidity::  " + context.getCurrentHumidity() + " %");

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

            if (context.isHumidifierState()) {
                context.setCurrentHumidity(context.getCurrentHumidity() + getRandomNumber());
            }

            if (!context.isHumidifierState() && !context.isDehumidiferState()) {
                context.setCurrentHumidity(context.getCurrentHumidity() + context.getDriftValue());
            }

            if (context.isDehumidiferState()) {
                context.setCurrentHumidity(context.getCurrentHumidity() - getRandomNumber());
            }

            postHumidity(context.getCurrentHumidity());
            messageWindow.WriteMessage("Current Humidity::  " + context.getCurrentHumidity() + " %");

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

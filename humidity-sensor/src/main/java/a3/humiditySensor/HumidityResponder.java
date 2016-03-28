package a3.humiditySensor;

import a3.message.Message;
import a3.message.MessageManagerInterface;
import a3.sensor.MessageResponder;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

import static a3.assist.RandomHelper.*;

/**
 * @author Weinan Qiu
 * @since 1.0.0
 */
public class HumidityResponder implements MessageResponder<Float> {

    @Autowired
    private MessageManagerInterface messageManager;

    @Override
    public boolean canRespondToMessageWithId(int id) {
        return -4 == id;
    }

    @Override
    public Float respondToMessage(Message message, Object context) {
        Float currentHumidity = (Float) ((List) context).get(0);
        Float driftValue = (Float) ((List) context).get(1);
        boolean humidiferState = false, dehumidiferState = false;

        if ("H1".equalsIgnoreCase(message.GetMessage())) {
            humidiferState = true;
        } else if ("H0".equalsIgnoreCase(message.GetMessage())) {
            humidiferState = false;
        } else if ("D1".equalsIgnoreCase(message.GetMessage())) {
            dehumidiferState = true;
        } else if ("D0".equalsIgnoreCase(message.GetMessage())) {
            dehumidiferState = false;
        }

        if (humidiferState) {
            currentHumidity += getRandomNumber();
        }

        if (!humidiferState && !dehumidiferState) {
            currentHumidity += driftValue;
        }

        if (dehumidiferState) {
            currentHumidity -= getRandomNumber();
        }

        postHumidity(currentHumidity);

        return currentHumidity;
    }

    private void postHumidity(Float humidity) {
        Message msg = new Message(2, String.valueOf(humidity));

        try {
            messageManager.SendMessage(msg);
        } catch (Exception e) {
            System.out.println("Error Posting Humidity:: " + e);
        }
    }
}

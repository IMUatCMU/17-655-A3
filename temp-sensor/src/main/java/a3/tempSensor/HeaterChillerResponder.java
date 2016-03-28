package a3.tempSensor;

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
public class HeaterChillerResponder implements MessageResponder<Float> {

    @Autowired
    private MessageManagerInterface messageManager;

    @Override
    public boolean canRespondToMessageWithId(int id) {
        return -5 == id;
    }

    @Override
    public Float respondToMessage(Message message, Object context) {
        Float currentTemp = (Float) ((List) context).get(0);
        Float driftValue = (Float) ((List) context).get(1);
        boolean heaterState = false, chillerState = false;

        if ("H1".equalsIgnoreCase(message.GetMessage())) {
            heaterState = true;
        } else if ("H0".equalsIgnoreCase(message.GetMessage())) {
            heaterState = false;
        } else if ("C1".equalsIgnoreCase(message.GetMessage())) {
            chillerState = true;
        } else if ("C0".equalsIgnoreCase(message.GetMessage())) {
            chillerState = false;
        }

        if (heaterState) {
            currentTemp += getRandomNumber();
        }

        if (!heaterState && !chillerState) {
            currentTemp += driftValue;
        }

        if (chillerState) {
            currentTemp -= getRandomNumber();
        }

        postTemperature(currentTemp);

        return currentTemp;
    }

    private void postTemperature(Float temp) {
        Message msg = new Message(1, String.valueOf(temp));

        try {
            messageManager.SendMessage(msg);
        } catch (Exception e) {
            System.out.println("Error Posting Temperature:: " + e);
        }
    }
}

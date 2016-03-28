package a3.tempSensor;

import a3.message.Message;
import a3.sensor.MessageResponder;

/**
 * @author Weinan Qiu
 * @since 1.0.0
 */
public class HeaterChillerResponder implements MessageResponder<TemperatureHandlingContext> {

    @Override
    public boolean canRespondToMessageWithId(int id) {
        return -5 == id;
    }

    @Override
    public TemperatureHandlingContext respondToMessage(Message message, Object context) {
        TemperatureHandlingContext tempContext = (TemperatureHandlingContext) context;

        if ("H1".equalsIgnoreCase(message.GetMessage())) {
            tempContext.setHeaterState(true);
        } else if ("H0".equalsIgnoreCase(message.GetMessage())) {
            tempContext.setHeaterState(false);
        } else if ("C1".equalsIgnoreCase(message.GetMessage())) {
            tempContext.setChillerState(true);
        } else if ("C0".equalsIgnoreCase(message.GetMessage())) {
            tempContext.setChillerState(false);
        }

        return tempContext;
    }
}

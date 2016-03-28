package a3.tempSensor;

import a3.message.Message;
import a3.message.MessageManagerInterface;
import a3.monitor.MessageWindow;
import a3.sensor.MessageResponder;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

import static a3.assist.RandomHelper.*;

/**
 * @author Weinan Qiu
 * @since 1.0.0
 */
public class HeaterChillerResponder implements MessageResponder<TemperatureHandlingContext> {

//    @Autowired
//    private MessageManagerInterface messageManager;
//
//    @Autowired
//    private MessageWindow messageWindow;

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

//    private void postTemperature(Float temp) {
//        Message msg = new Message(1, String.valueOf(temp));
//
//        try {
//            messageManager.SendMessage(msg);
//        } catch (Exception e) {
//            System.out.println("Error Posting Temperature:: " + e);
//        }
//    }
}

package a3.humiditySensor;

import a3.message.Message;
import a3.message.MessageManagerInterface;
import a3.sensor.MessageResponder;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Weinan Qiu
 * @since 1.0.0
 */
public class HumidityResponder implements MessageResponder<HumidityContext> {

    @Autowired
    private MessageManagerInterface messageManager;

    @Override
    public boolean canRespondToMessageWithId(int id) {
        return -4 == id;
    }

    @Override
    public HumidityContext respondToMessage(Message message, Object object) {
        HumidityContext context = (HumidityContext) object;

        if ("H1".equalsIgnoreCase(message.GetMessage())) {
            context.setHumidifierState(true);
        } else if ("H0".equalsIgnoreCase(message.GetMessage())) {
            context.setHumidifierState(false);
        } else if ("D1".equalsIgnoreCase(message.GetMessage())) {
            context.setDehumidiferState(true);
        } else if ("D0".equalsIgnoreCase(message.GetMessage())) {
            context.setDehumidiferState(false);
        }

        return context;
    }
}

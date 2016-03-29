package a3.fireSensor;

import a3.message.Message;
import a3.monitor.MessageWindow;
import a3.sensor.MessageResponder;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Weinan Qiu
 * @since 1.0.0
 */
public class SprinklerResponder implements MessageResponder<FireContext> {

    @Autowired
    private MessageWindow messageWindow;

    @Override
    public boolean canRespondToMessageWithId(int id) {
        return -10 == id;
    }

    @Override
    public FireContext respondToMessage(Message message, Object object) {
        FireContext context = (FireContext) object;

        int water = Integer.parseInt(message.GetMessage());
        context.setFireLevel(context.getFireLevel() - water);
        messageWindow.WriteMessage("Received " + water + " level of water from sprinkler.");

        return context;
    }
}

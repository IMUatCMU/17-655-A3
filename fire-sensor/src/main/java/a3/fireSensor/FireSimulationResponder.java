package a3.fireSensor;

import a3.message.Message;
import a3.sensor.MessageResponder;

/**
 * {@link MessageResponder} implementation for responding to fire simulation request.
 *
 * @author Weinan Qiu
 * @since 1.0.0
 */
public class FireSimulationResponder implements MessageResponder<FireContext> {

    @Override
    public boolean canRespondToMessageWithId(int id) {
        return 8 == id;
    }

    @Override
    public FireContext respondToMessage(Message message, Object object) {
        FireContext context = (FireContext) object;
        context.setFireLevel(Integer.parseInt(message.GetMessage()));
        return context;
    }
}

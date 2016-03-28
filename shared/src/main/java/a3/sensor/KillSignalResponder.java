package a3.sensor;

import a3.message.Message;

/**
 * @author Weinan Qiu
 * @since 1.0.0
 */
public class KillSignalResponder implements MessageResponder<Void> {

    @Override
    public boolean canRespondToMessageWithId(int id) {
        return 99 == id;
    }

    @Override
    public Void respondToMessage(Message message, Object context) {
        throw new SensorQuitSignal();
    }
}

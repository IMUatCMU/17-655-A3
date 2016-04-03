package a3.sensor;

import a3.message.Message;

/**
 * {@link MessageResponder} implementation to handle message id 99 for shutting down.
 *
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

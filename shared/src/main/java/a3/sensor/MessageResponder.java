package a3.sensor;

import a3.message.Message;

/**
 * Common interface for delegate message handling capability to others
 *
 * @author Weinan Qiu
 * @since 1.0.0
 */
public interface MessageResponder<T> {

    boolean canRespondToMessageWithId(int id);

    T respondToMessage(Message message, Object context);

    default T respondToMessage(Message message) {
        return respondToMessage(message, null);
    }
}

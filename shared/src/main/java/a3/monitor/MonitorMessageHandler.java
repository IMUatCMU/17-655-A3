package a3.monitor;

import a3.message.Message;

/**
 * Common interface for monitor to delegate message handling capability to others
 *
 * @since 1.0.0
 */
public interface MonitorMessageHandler {

    boolean ON = true;
    boolean OFF = false;

    boolean canHandleMessageWithId(int id);

    void handleMessage(Message message);
}

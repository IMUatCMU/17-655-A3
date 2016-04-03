package a3.fireAlarm;

import a3.message.Message;
import a3.monitor.Indicator;
import a3.monitor.MessageWindow;
import a3.monitor.MonitorMessageHandler;
import javax.annotation.Resource;
import javax.management.monitor.Monitor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * {@link MonitorMessageHandler} implementation to handle id -8 messages.
 *
 * @author Weinan Qiu
 * @since 1.0.0
 */
public class AlarmControlResponder implements MonitorMessageHandler {

    @Autowired
    private MessageWindow messageWindow;

    @Resource(name = "fireAlarmIndicator")
    private Indicator fireAlarmIndicator;

    @Override
    public boolean canHandleMessageWithId(int id) {
        return -8 == id;
    }

    @Override
    public void handleMessage(Message message, Object context) {

        if ("F1".equalsIgnoreCase(message.GetMessage())) {
            fireAlarmIndicator.SetLampColorAndMessage("Fire Alarm ON", 3);
            messageWindow.WriteMessage("Received fire alarm ON instruction");
        } else if ("F0".equalsIgnoreCase(message.GetMessage())) {
            fireAlarmIndicator.SetLampColorAndMessage("Fire Alarm OFF", 0);
            messageWindow.WriteMessage("Received fire alarm OFF instruction");
        }
    }
}

package a3.intrusionController;

import a3.message.Message;
import a3.message.MessageManagerInterface;
import a3.monitor.Indicator;
import a3.monitor.MessageWindow;
import a3.monitor.MonitorMessageHandler;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * {@link MonitorMessageHandler} implementation to handle status information for the intrusion alarms.
 *
 * @author Weinan Qiu
 * @since 1.0.0
 */
public class StatusInformationHandler implements MonitorMessageHandler {

    @Autowired
    private MessageWindow messageWindow;

    @Resource(name = "windowAlarm")
    private Indicator windowAlarm;

    @Resource(name = "doorAlarm")
    private Indicator doorAlarm;

    @Resource(name = "motionAlarm")
    private Indicator motionAlarm;

    @Override
    public boolean canHandleMessageWithId(int id) {
        return 6 == id;
    }

    @Override
    public void handleMessage(Message message, Object context) {

        if ("W1".equalsIgnoreCase(message.GetMessage())) {
            windowAlarm.SetLampColorAndMessage("WINDOW BREAK", 3);
            messageWindow.WriteMessage("Window intrusion reported.");
        } else if ("W0".equalsIgnoreCase(message.GetMessage())) {
            windowAlarm.SetLampColorAndMessage("WINDOW NORM", 1);
            messageWindow.WriteMessage("Window status is normal.");
        } else if ("D1".equalsIgnoreCase(message.GetMessage())) {
            doorAlarm.SetLampColorAndMessage("DOOR BREAK", 3);
            messageWindow.WriteMessage("Door intrusion reported.");
        } else if ("D0".equalsIgnoreCase(message.GetMessage())) {
            doorAlarm.SetLampColorAndMessage("DOOR NORM", 1);
            messageWindow.WriteMessage("Door status is normal.");
        } else if ("M1".equalsIgnoreCase(message.GetMessage())) {
            motionAlarm.SetLampColorAndMessage("MOTION DETECT", 3);
            messageWindow.WriteMessage("Motion detection reported.");
        } else if ("M0".equalsIgnoreCase(message.GetMessage())) {
            motionAlarm.SetLampColorAndMessage("MOTION NORM", 1);
            messageWindow.WriteMessage("Motion status is normal.");
        }
    }
}

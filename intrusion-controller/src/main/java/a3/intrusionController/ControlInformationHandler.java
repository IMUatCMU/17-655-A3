package a3.intrusionController;

import a3.message.Message;
import a3.monitor.Indicator;
import a3.monitor.MessageWindow;
import a3.monitor.MonitorMessageHandler;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * {@link MonitorMessageHandler} implementation to handle intrusion alarm control message (id = -6)
 *
 * @author Weinan Qiu
 * @since 1.0.0
 */
public class ControlInformationHandler implements MonitorMessageHandler {

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
        return -6 == id;
    }

    @Override
    public void handleMessage(Message message, Object context) {
        if ("W1".equalsIgnoreCase(message.GetMessage())) {
            windowAlarm.SetLampColorAndMessage("WINDOW ARMED", 2);
            messageWindow.WriteMessage("Window alarm is armed");
        } else if ("W0".equalsIgnoreCase(message.GetMessage())) {
            windowAlarm.SetLampColorAndMessage("WINDOW DISARMED", 0);
            messageWindow.WriteMessage("Window alarm is disarmed.");
        } else if ("D1".equalsIgnoreCase(message.GetMessage())) {
            doorAlarm.SetLampColorAndMessage("DOOR ARMED", 2);
            messageWindow.WriteMessage("Door intrusion is armed.");
        } else if ("D0".equalsIgnoreCase(message.GetMessage())) {
            doorAlarm.SetLampColorAndMessage("DOOR DISARMED", 0);
            messageWindow.WriteMessage("Door status is disarmed.");
        } else if ("M1".equalsIgnoreCase(message.GetMessage())) {
            motionAlarm.SetLampColorAndMessage("MOTION ARMED", 2);
            messageWindow.WriteMessage("Motion detection is armed.");
        } else if ("M0".equalsIgnoreCase(message.GetMessage())) {
            motionAlarm.SetLampColorAndMessage("MOTION DISARMED", 0);
            messageWindow.WriteMessage("Motion status is disarmed.");
        }
    }
}

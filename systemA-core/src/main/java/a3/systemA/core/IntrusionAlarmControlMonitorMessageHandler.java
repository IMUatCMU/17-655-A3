package a3.systemA.core;

import a3.message.Message;
import a3.monitor.Indicator;
import a3.monitor.MessageWindow;
import a3.monitor.MonitorMessageHandler;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Monitor message responders for intrusion control signal
 *
 * @author Weinan Qiu
 * @since 1.0.0
 */
public class IntrusionAlarmControlMonitorMessageHandler implements MonitorMessageHandler {

    @Resource(name = "windowIndicator")
    private Indicator windowIndicator;

    @Resource(name = "doorIndicator")
    private Indicator doorIndicator;

    @Resource(name = "motionIndicator")
    private Indicator motionIndicator;

    @Autowired
    private MessageWindow messageWindow;

    @Override
    public boolean canHandleMessageWithId(int id) {
        return -6 == id;
    }

    @Override
    public void handleMessage(Message message, Object context) {

        if ("W1".equalsIgnoreCase(message.GetMessage())) {
            messageWindow.WriteMessage("Window armed!");
            windowIndicator.SetLampColorAndMessage("WINDOW ARM", 2);
        } else if ("W0".equalsIgnoreCase(message.GetMessage())) {
            messageWindow.WriteMessage("Window disarmed");
            windowIndicator.SetLampColorAndMessage("WINDOW DISARM", 0);
        } else if ("D1".equalsIgnoreCase(message.GetMessage())) {
            messageWindow.WriteMessage("Door armed");
            doorIndicator.SetLampColorAndMessage("DOOR ARM", 2);
        } else if ("D0".equalsIgnoreCase(message.GetMessage())) {
            messageWindow.WriteMessage("Door disarmed");
            doorIndicator.SetLampColorAndMessage("WINDOW DISARM", 0);
        } else if ("M1".equalsIgnoreCase(message.GetMessage())) {
            messageWindow.WriteMessage("Motion armed");
            motionIndicator.SetLampColorAndMessage("MOTION ARM", 2);
        } else if ("M0".equalsIgnoreCase(message.GetMessage())) {
            messageWindow.WriteMessage("Motion disarmed");
            motionIndicator.SetLampColorAndMessage("MOTION DISARM", 0);
        }
    }
}

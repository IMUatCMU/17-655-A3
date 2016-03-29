package a3.systemA.core;

import a3.message.Message;
import a3.monitor.Indicator;
import a3.monitor.MessageWindow;
import a3.monitor.MonitorMessageHandler;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Weinan Qiu
 * @since 1.0.0
 */
public class IntrusionSensorStatusMonitorMessageHandler implements MonitorMessageHandler {

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
        return 6 == id;
    }

    @Override
    public void handleMessage(Message message, Object context) {

        if ("W1".equalsIgnoreCase(message.GetMessage())) {
            messageWindow.WriteMessage("Received window report: INTRUSION!");
            windowIndicator.SetLampColorAndMessage("WINDOW BREAKIN", 3);
        } else if ("W0".equalsIgnoreCase(message.GetMessage())) {
            messageWindow.WriteMessage("Received window report: normal");
            windowIndicator.SetLampColorAndMessage("WINDOW NORM", 1);
        } else if ("D1".equalsIgnoreCase(message.GetMessage())) {
            messageWindow.WriteMessage("Received door report: INTRUSION!");
            doorIndicator.SetLampColorAndMessage("DOOR BREAKIN", 3);
        } else if ("D0".equalsIgnoreCase(message.GetMessage())) {
            messageWindow.WriteMessage("Received door report: normal");
            doorIndicator.SetLampColorAndMessage("WINDOW NORM", 1);
        } else if ("M1".equalsIgnoreCase(message.GetMessage())) {
            messageWindow.WriteMessage("Received motion report: INTRUSION!");
            motionIndicator.SetLampColorAndMessage("MOTION BREAKIN", 3);
        } else if ("M0".equalsIgnoreCase(message.GetMessage())) {
            messageWindow.WriteMessage("Received motion report: normal");
            motionIndicator.SetLampColorAndMessage("MOTION NORM", 1);
        }
    }
}

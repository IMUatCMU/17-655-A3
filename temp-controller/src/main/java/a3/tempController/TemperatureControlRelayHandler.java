package a3.tempController;

import a3.message.Message;
import a3.message.MessageManagerInterface;
import a3.monitor.Indicator;
import a3.monitor.MessageWindow;
import a3.monitor.MonitorMessageHandler;
import javax.annotation.Resource;
import javax.management.monitor.Monitor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * {@link MonitorMessageHandler} implementation to replay the temperature control message to the sensors
 * for simulation purpose.
 *
 * @author Weinan Qiu
 * @since 1.0.0
 */
public class TemperatureControlRelayHandler implements MonitorMessageHandler {

    @Autowired
    private MessageManagerInterface messageManager;

    @Autowired
    private MessageWindow messageWindow;

    @Resource(name = "heaterIndicator")
    private Indicator heaterIndicator;

    @Resource(name = "chillerIndicator")
    private Indicator chillerIndicator;

    @Override
    public boolean canHandleMessageWithId(int id) {
        return 5 == id;
    }

    @Override
    public void handleMessage(Message message, Object context) {
        relayMessage(message.GetMessage());

        if ("H1".equalsIgnoreCase(message.GetMessage())) {
            heaterIndicator.SetLampColorAndMessage("HEATER ON", 1);
            messageWindow.WriteMessage("Received heater on message");
        } else if ("H0".equalsIgnoreCase(message.GetMessage())) {
            heaterIndicator.SetLampColorAndMessage("HEATER OFF", 0);
            messageWindow.WriteMessage("Received heater off message");
        } else if ("C1".equalsIgnoreCase(message.GetMessage())) {
            chillerIndicator.SetLampColorAndMessage("CHILLER ON", 1);
            messageWindow.WriteMessage("Received chiller on message");
        } else if ("C0".equalsIgnoreCase(message.GetMessage())) {
            chillerIndicator.SetLampColorAndMessage("CHILLER OFF", 0);
            messageWindow.WriteMessage("Received chiller off message");
        }
    }

    private void relayMessage(String m) {
        Message msg = new Message(-5, m);
        try {
            messageManager.SendMessage(msg);
        } catch (Exception e) {
            System.out.println("Error Confirming Message:: " + e);
        }
        messageWindow.WriteMessage("Sent new control message");
    }
}

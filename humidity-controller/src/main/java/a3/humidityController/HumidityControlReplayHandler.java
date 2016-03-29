package a3.humidityController;

import a3.message.Message;
import a3.message.MessageManagerInterface;
import a3.monitor.Indicator;
import a3.monitor.MessageWindow;
import a3.monitor.MonitorMessageHandler;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Weinan Qiu
 * @since 1.0.0
 */
public class HumidityControlReplayHandler implements MonitorMessageHandler {

    @Autowired
    private MessageManagerInterface messageManager;

    @Autowired
    private MessageWindow messageWindow;

    @Resource(name = "humidityIndicator")
    private Indicator humidityIndicator;

    @Resource(name = "dehumidityIndicator")
    private Indicator dehumidityIndicator;

    @Override
    public boolean canHandleMessageWithId(int id) {
        return 4 == id;
    }

    @Override
    public void handleMessage(Message message, Object context) {
        relayMessage(message.GetMessage());

        if ("H1".equalsIgnoreCase(message.GetMessage())) {
            humidityIndicator.SetLampColorAndMessage("HUMID ON", 1);
            messageWindow.WriteMessage("Received humidifier on message");
        } else if ("H0".equalsIgnoreCase(message.GetMessage())) {
            humidityIndicator.SetLampColorAndMessage("HUMID OFF", 0);
            messageWindow.WriteMessage("Received humidifier off message");
        } else if ("D1".equalsIgnoreCase(message.GetMessage())) {
            dehumidityIndicator.SetLampColorAndMessage("DEHUMID ON", 1);
            messageWindow.WriteMessage("Received dehumidifier on message");
        } else if ("D0".equalsIgnoreCase(message.GetMessage())) {
            dehumidityIndicator.SetLampColorAndMessage("DEHUMID OFF", 0);
            messageWindow.WriteMessage("Received dehumidifier off message");
        }
    }

    private void relayMessage(String m) {
        Message msg = new Message(-4, m);
        try {
            messageManager.SendMessage(msg);
        } catch (Exception e) {
            System.out.println("Error Confirming Message:: " + e);
        }
        messageWindow.WriteMessage("Sent new control message");
    }
}

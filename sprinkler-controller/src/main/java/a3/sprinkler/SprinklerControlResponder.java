package a3.sprinkler;

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
public class SprinklerControlResponder implements MonitorMessageHandler {

    @Autowired
    private MessageWindow messageWindow;

    @Autowired
    private MessageManagerInterface messageManager;

    @Resource(name = "sprinklerIndicator")
    private Indicator sprinklerIndicator;

    private int waterLevel = 5;

    @Override
    public boolean canHandleMessageWithId(int id) {
        return -9 == id;
    }

    @Override
    public void handleMessage(Message message, Object context) {
        try {
            if ("S1".equalsIgnoreCase(message.GetMessage())) {
                messageWindow.WriteMessage("Received sprinkler ON message");
                sprinklerIndicator.SetLampColorAndMessage("Sprinkler ON", 1);

                Message m = new Message(-10, String.valueOf(waterLevel));
                messageManager.SendMessage(m);
            } else if ("S0".equalsIgnoreCase(message.GetMessage())) {
                messageWindow.WriteMessage("Received sprinkler OFF message");
                sprinklerIndicator.SetLampColorAndMessage("Sprinkler OFF", 0);
            }
        } catch (Exception ex) {
            messageWindow.WriteMessage("Error posting sprinkler information:: " + ex.getMessage());
        }
    }

    public int getWaterLevel() {
        return waterLevel;
    }

    public void setWaterLevel(int waterLevel) {
        this.waterLevel = waterLevel;
    }
}

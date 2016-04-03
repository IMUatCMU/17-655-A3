package a3.monitor;

import a3.message.Message;
import a3.message.MessageManagerInterface;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * {@link MonitorMessageHandler} implementation responsible for temperature control. Refactored from
 * the original framework provided.
 *
 * @author Weinan Qiu
 * @since 1.0.0
 */
public class TemperatureMonitorMessageHandler implements MonitorMessageHandler {

    @Resource(name = "messageWindow")
    private MessageWindow messageWindow;

    @Resource(name = "tempIndicator")
    private Indicator indicator;

    @Autowired
    MessageManagerInterface messageManager;

    private Float low = 0f;
    private Float high = 100f;

    @Override
    public boolean canHandleMessageWithId(int id) {
        return 1 == id;
    }

    @Override
    public void handleMessage(Message message, Object context) {
        Float temperature;
        try {
            temperature = Float.valueOf(message.GetMessage());
        } catch (Exception e) {
            messageWindow.WriteMessage("Error reading temperature: " + e.getMessage());
            return;
        }
        messageWindow.WriteMessage("Temperature:: " + temperature);

        if (temperature < low) {
            indicator.SetLampColorAndMessage("TEMP LOW", 3);
            Heater(ON);
            Chiller(OFF);
        } else if (temperature > high) {
            indicator.SetLampColorAndMessage("TEMP HIGH", 3);
            Heater(OFF);
            Chiller(ON);
        } else {
            indicator.SetLampColorAndMessage("TEMP OK", 1);
            Heater(OFF);
            Chiller(OFF);
        }
    }

    private void Heater(boolean ON) {
        Message msg;
        if (ON) {
            msg = new Message(5, "H1");
        } else {
            msg = new Message(5, "H0");
        }

        try {
            messageManager.SendMessage(msg);
        } catch (Exception e) {
            messageWindow.WriteMessage("Error sending heater control message:: " + e.getMessage());
        }
    }

    private void Chiller(boolean ON) {
        Message msg;
        if (ON) {
            msg = new Message(5, "C1");
        } else {
            msg = new Message(5, "C0");
        }

        try {
            messageManager.SendMessage(msg);
        } catch (Exception e) {
            messageWindow.WriteMessage("Error sending chiller control message:: " + e.getMessage());
        }
    }

    public Float getLow() {
        return low;
    }

    public void setLow(Float low) {
        this.low = low;
        messageWindow.WriteMessage("Temperature lower limit changed to:: " + this.low);
    }

    public Float getHigh() {
        return high;
    }

    public void setHigh(Float high) {
        this.high = high;
        messageWindow.WriteMessage("Temperature upper limit changed to:: " + this.high);
    }
}

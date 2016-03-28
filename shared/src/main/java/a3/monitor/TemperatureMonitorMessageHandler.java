package a3.monitor;

import a3.message.Message;
import javax.annotation.Resource;

/**
 * @author Weinan Qiu
 * @since 1.0.0
 */
public class TemperatureMonitorMessageHandler implements MonitorMessageHandler {

    @Resource(name = "messageWindow")
    private MessageWindow messageWindow;

    private Float low = 0f;
    private Float high = 100f;

    @Override
    public boolean canHandleMessageWithId(int id) {
        return 1 == id;
    }

    @Override
    public void handleMessage(Message message) {
        Float temperature;
        try {
            temperature = Float.valueOf(message.GetMessage());
        } catch (Exception e) {
            messageWindow.WriteMessage("Error reading temperature: " + e.getMessage());
            return;
        }

        messageWindow.WriteMessage("Temperature:: " + temperature);
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

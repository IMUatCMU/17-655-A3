package a3.monitor;

import a3.message.Message;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Weinan Qiu
 * @since 1.0.0
 */
public class HumidityMonitorMessageHandler implements MonitorMessageHandler {

    @Autowired
    private MessageWindow messageWindow;

    private Float low;
    private Float high;

    @Override
    public boolean canHandleMessageWithId(int id) {
        return 2 == id;
    }

    @Override
    public void handleMessage(Message message) {
        Float humidity;
        try {
            humidity = Float.valueOf(message.GetMessage());
        } catch (Exception e) {
            messageWindow.WriteMessage("Error reading humidity: " + e.getMessage());
            return;
        }

        messageWindow.WriteMessage("Humidity:: " + humidity);
    }

    public Float getLow() {
        return low;
    }

    public void setLow(Float low) {
        this.low = low;
        messageWindow.WriteMessage("Humidity lower limit changed to:: " + this.low);
    }

    public Float getHigh() {
        return high;
    }

    public void setHigh(Float high) {
        this.high = high;
        messageWindow.WriteMessage("Humidity upper limit changed to:: " + this.high);
    }
}

package a3.monitor;

import a3.message.Message;
import a3.message.MessageManagerInterface;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * {@link MonitorMessageHandler} implementation to handle humidity control. It is refactored from
 * the original framework provided.
 *
 * @author Weinan Qiu
 * @since 1.0.0
 */
public class HumidityMonitorMessageHandler implements MonitorMessageHandler {

    @Autowired
    private MessageWindow messageWindow;

    @Autowired
    private MessageManagerInterface messageManager;

    @Resource(name = "humidIndicator")
    private Indicator indicator;

    private Float low = 0.0f;
    private Float high = 100.0f;

    @Override
    public boolean canHandleMessageWithId(int id) {
        return 2 == id;
    }

    @Override
    public void handleMessage(Message message, Object context) {
        Float humidity;
        try {
            humidity = Float.valueOf(message.GetMessage());
        } catch (Exception e) {
            messageWindow.WriteMessage("Error reading humidity: " + e.getMessage());
            return;
        }
        messageWindow.WriteMessage("Humidity:: " + humidity);

        if (humidity < low) {
            indicator.SetLampColorAndMessage("HUMI LOW", 3);
            Humidifier(ON);
            Dehumidifier(OFF);
        } else if (humidity > high) {
            indicator.SetLampColorAndMessage("HUMI HIGH", 3);
            Humidifier(OFF);
            Dehumidifier(ON);
        } else {
            indicator.SetLampColorAndMessage("HUMI OK", 1);
            Humidifier(OFF);
            Dehumidifier(OFF);
        }
    }

    private void Humidifier(boolean ON) {
        Message msg;
        if (ON) {
            msg = new Message(4, "H1");
        } else {
            msg = new Message(4, "H0");
        }

        try {
            messageManager.SendMessage(msg);
        } catch (Exception e) {
            messageWindow.WriteMessage("Error sending humidifier control message::  " + e.getMessage());
        }
    }

    private void Dehumidifier(boolean ON) {
        Message msg;
        if (ON) {
            msg = new Message(4, "D1");
        } else {
            msg = new Message(4, "D0");
        }

        try {
            messageManager.SendMessage(msg);
        } catch (Exception e) {
            messageWindow.WriteMessage("Error sending dehumidifier control message::  " + e);
        }
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

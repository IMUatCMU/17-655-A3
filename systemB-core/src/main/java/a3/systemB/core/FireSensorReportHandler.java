package a3.systemB.core;

import a3.message.Message;
import a3.message.MessageManagerInterface;
import a3.monitor.Indicator;
import a3.monitor.MessageWindow;
import a3.monitor.MonitorMessageHandler;
import java.util.Date;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Message handler for fire level report
 *
 * @author Weinan Qiu
 * @since 1.0.0
 */
public class FireSensorReportHandler implements MonitorMessageHandler {

    private boolean extinguishingInProgress = false;
    private Date firstFireReportTime = null;

    @Resource(name = "fireIndicator")
    private Indicator fireIndicator;

    @Autowired
    private MessageWindow messageWindow;

    @Autowired
    private MessageManagerInterface messageManager;

    @Override
    public boolean canHandleMessageWithId(int id) {
        return 9 == id;
    }

    @Override
    public void handleMessage(Message message, Object context) {
        Integer fireLevel = Integer.parseInt(message.GetMessage());
        messageWindow.WriteMessage("Received fire level report:: " + fireLevel);

        if (fireLevel <= 0) {
            fireIndicator.SetLampColorAndMessage("NO FIRE", 0);

            if (firstFireReportTime != null)
                firstFireReportTime = null;

            if (extinguishingInProgress) {
                extinguishingInProgress = false;

                Message stopFireAlarm = new Message(-8, "F0");
                Message stopSprinkler = new Message(-9, "S0");

                try {
                    messageManager.SendMessage(stopFireAlarm);
                    messageManager.SendMessage(stopSprinkler);
                } catch (Exception ex) {
                    messageWindow.WriteMessage("Failed to stop fire alarm and/or sprinkler:: " + ex.getMessage());
                }
            }
        } else {
            fireIndicator.SetLampColorAndMessage("FIRE", 3);

            startFileAlarm();

            if (firstFireReportTime == null) {
                firstFireReportTime = new Date();
                new ConfirmDialog(
                        "Start Sprinkler? (Auto-start in 10 seconds)",
                        10,
                        () -> {
                            extinguishingInProgress = true;
                            startSprinkler();
                            messageWindow.WriteMessage("User started sprinkler.");
                        },
                        () -> messageWindow.WriteMessage("User cancelled sprinkler action."),
                        () -> {

                            startSprinkler();
                            messageWindow.WriteMessage("Sprinkler automatically started upon lack of action.");
                        });
            }

            if (extinguishingInProgress) {
                startSprinkler();
            }
        }
    }

    public void startSprinkler() {
        extinguishingInProgress = true;
        Message startSprinkler = new Message(-9, "S1");
        try {
            messageManager.SendMessage(startSprinkler);
        } catch (Exception ex) {
            messageWindow.WriteMessage("Failed to start sprinkler:: " + ex.getMessage());
        }
    }

    public void startFileAlarm() {
        Message startFireAlarm = new Message(-8, "F1");
        try {
            messageManager.SendMessage(startFireAlarm);
        } catch (Exception ex) {
            messageWindow.WriteMessage("Failed to start fire alarm:: " + ex.getMessage());
        }
    }

    public void stopSprinkler() {
        extinguishingInProgress = false;
        Message stopSprinkler = new Message(-9, "S0");
        try {
            messageManager.SendMessage(stopSprinkler);
        } catch (Exception ex) {
            messageWindow.WriteMessage("Failed to stop sprinkler:: " + ex.getMessage());
        }
    }
}

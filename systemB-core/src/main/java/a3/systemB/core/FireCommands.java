package a3.systemB.core;

import a3.message.Message;
import a3.message.MessageManagerInterface;
import a3.monitor.MessageWindow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;

/**
 * @author Weinan Qiu
 * @since 1.0.0
 */
@Component
public class FireCommands implements CommandMarker {

    @Autowired
    private MessageWindow messageWindow;

    @Autowired
    private MessageManagerInterface messageManager;

    @Autowired
    private FireSensorReportHandler fireSensorReportHandler;

    @CliCommand(value = "fire simulate", help = "Simulate fire with level of 0 to 50")
    public String simulateFire(@CliOption(key = "level", mandatory = true, help = "Level of the fire, 0 to 50") Integer level) {
        if (level < 0 || level > 50) {
            return "Please specify a level between 0 to 50";
        }

        try {
            Message m = new Message(8, String.valueOf(level));
            messageManager.SendMessage(m);
        } catch (Exception ex) {
            return "Failed to send fire simulation message";
        }

        messageWindow.WriteMessage("Fire simulation set with level: " + level);
        return "Fire simulated with level: " + level;
    }

    @CliCommand(value = "fire sprinkler on", help = "Put out fire")
    public String turnOnSprinkler() {
        fireSensorReportHandler.startSprinkler();
        return "Done";
    }

    @CliCommand(value = "fire sprinkler off", help = "Turn off sprinkler")
    public String turnOffSprinkler() {
        fireSensorReportHandler.stopSprinkler();
        return "Done";
    }
}

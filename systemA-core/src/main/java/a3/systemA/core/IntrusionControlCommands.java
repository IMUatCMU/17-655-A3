package a3.systemA.core;

import a3.message.Message;
import a3.message.MessageManagerInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;

/**
 * Intrusion console commands for control purpose
 *
 * @author Weinan Qiu
 * @since 1.0.0
 */
@Component
public class IntrusionControlCommands implements CommandMarker {

    @Autowired
    private MessageManagerInterface messageManager;

    @CliCommand(value = "intrusion-alarm arm", help = "Arm intrusion detection system.")
    public String armAlarm(@CliOption(
            key = "target",
            mandatory = true,
            help = "Target of the alarm [window|door|motion|all]") String target) {
        try {
            if ("window".equalsIgnoreCase(target)) {
                Message m = new Message(-6, "W1");
                messageManager.SendMessage(m);

                return "Armed window alarm";
            } else if ("door".equalsIgnoreCase(target)) {
                Message m = new Message(-6, "D1");
                messageManager.SendMessage(m);

                return "Armed door alarm";
            } else if ("motion".equalsIgnoreCase(target)) {
                Message m = new Message(-6, "M1");
                messageManager.SendMessage(m);

                return "Armed motion alarm";
            } else if ("all".equalsIgnoreCase(target)) {
                Message m1 = new Message(-6, "W1");
                Message m2 = new Message(-6, "D1");
                Message m3 = new Message(-6, "M1");

                messageManager.SendMessage(m1);
                messageManager.SendMessage(m2);
                messageManager.SendMessage(m3);

                return "Armed window, door and motion alarms";
            } else {
                return "target value must be in [window|door|motion|all]";
            }
        } catch (Exception ex) {
            return "Message sending failed:: " + ex.getMessage();
        }
    }

    @CliCommand(value = "intrusion-alarm disarm", help = "disarm intrusion detection system.")
    public String disarmAlarm(@CliOption(
            key = "target",
            mandatory = true,
            help = "Target of the alarm [window|door|motion|all]") String target) {
        try {
            if ("window".equalsIgnoreCase(target)) {
                Message m = new Message(-6, "W0");
                messageManager.SendMessage(m);

                return "Disarmed window alarm";
            } else if ("door".equalsIgnoreCase(target)) {
                Message m = new Message(-6, "D0");
                messageManager.SendMessage(m);

                return "Disarmed door alarm";
            } else if ("motion".equalsIgnoreCase(target)) {
                Message m = new Message(-6, "M0");
                messageManager.SendMessage(m);

                return "Disarmed motion alarm";
            } else if ("all".equalsIgnoreCase(target)) {
                Message m1 = new Message(-6, "W0");
                Message m2 = new Message(-6, "D0");
                Message m3 = new Message(-6, "M0");

                messageManager.SendMessage(m1);
                messageManager.SendMessage(m2);
                messageManager.SendMessage(m3);

                return "Disarmed window, door and motion alarms";
            } else {
                return "target value must be in [window|door|motion|all]";
            }
        } catch (Exception ex) {
            return "Message sending failed:: " + ex.getMessage();
        }
    }
}

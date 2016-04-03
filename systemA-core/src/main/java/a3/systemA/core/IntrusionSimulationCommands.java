package a3.systemA.core;

import a3.message.Message;
import a3.message.MessageManagerInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;

/**
 * Intrusion console commands for simulation purpose
 *
 * @author Weinan Qiu
 * @since 1.0.0
 */
@Component
public class IntrusionSimulationCommands implements CommandMarker {

    @Autowired
    private MessageManagerInterface messageManager;

    @CliCommand(value = "break-in simulate", help = "Simulate a break in event")
    public String simulateBreakIn(@CliOption(
            key = "target",
            mandatory = true,
            help = "Target of the break in event [window|door|motion|all]") String target) {

        try {
            if ("window".equalsIgnoreCase(target)) {
                Message m = new Message(7, "W1");
                messageManager.SendMessage(m);

                return "Simulated break in event from window";
            } else if ("door".equalsIgnoreCase(target)) {
                Message m = new Message(7, "D1");
                messageManager.SendMessage(m);

                return "Simulated break in event from door";
            } else if ("motion".equalsIgnoreCase(target)) {
                Message m = new Message(7, "M1");
                messageManager.SendMessage(m);

                return "Simulated break in event from motion";
            } else if ("all".equalsIgnoreCase(target)) {
                Message m1 = new Message(7, "W1");
                Message m2 = new Message(7, "D1");
                Message m3 = new Message(7, "M1");

                messageManager.SendMessage(m1);
                messageManager.SendMessage(m2);
                messageManager.SendMessage(m3);

                return "Simulated break in event from window, door and motion";
            } else {
                return "target value must be in [window|door|motion|all]";
            }
        } catch (Exception ex) {
            return "Message sending failed:: " + ex.getMessage();
        }
    }

    @CliCommand(value = "break-in reset", help = "Reset a break in event")
    public String resetBreakIn(@CliOption(
            key = "target",
            mandatory = true,
            help = "Target of the break in event [window|door|motion|all]") String target) {

        try {
            if ("window".equalsIgnoreCase(target)) {
                Message m = new Message(7, "W0");
                messageManager.SendMessage(m);

                return "Reset break in event from window";
            } else if ("door".equalsIgnoreCase(target)) {
                Message m = new Message(7, "D0");
                messageManager.SendMessage(m);

                return "Reset break in event from door";
            } else if ("motion".equalsIgnoreCase(target)) {
                Message m = new Message(7, "M0");
                messageManager.SendMessage(m);

                return "Reset break in event from motion";
            } else if ("all".equalsIgnoreCase(target)) {
                Message m1 = new Message(7, "W0");
                Message m2 = new Message(7, "D0");
                Message m3 = new Message(7, "M0");

                messageManager.SendMessage(m1);
                messageManager.SendMessage(m2);
                messageManager.SendMessage(m3);

                return "Reset break in event from window, door and motion";
            } else {
                return "target value must be in [window|door|motion|all]";
            }
        } catch (Exception ex) {
            return "Message sending failed:: " + ex.getMessage();
        }
    }
}

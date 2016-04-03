package a3.console;

import a3.message.Message;
import a3.message.MessageManagerInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.stereotype.Component;

/**
 * Commands to send out 99 message.
 *
 * @author Weinan Qiu
 * @since 1.0.0
 */
@Component
public class CloseCommands implements CommandMarker {

    @Autowired
    private MessageManagerInterface messageManager;

    @CliCommand(value = {"x", "X", "close", "q"}, help = "properly quit the program")
    public String close() {
        Message msg = new Message(99, "XXX");
        try {
            messageManager.SendMessage(msg);
        } catch (Exception ex) {
            System.out.println("Error shutting down..." + ex.getMessage());
        }

        return "Shutdown request";
    }
}

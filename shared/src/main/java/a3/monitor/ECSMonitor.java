package a3.monitor;

import a3.message.Message;
import a3.message.MessageManagerInterface;
import a3.message.MessageQueue;
import java.util.List;
import javax.annotation.Resource;

/**
 * @since 1.0.0
 */
public class ECSMonitor extends Thread {

    private List<MonitorUI> monitorUIs;
    private List<MonitorMessageHandler> messageHandlers;
    private MessageManagerInterface messageManager;
    private boolean registered = true;

    @Resource(name = "messageWindow")
    MessageWindow messageWindow;

    @Override
    public void run() {
        this.monitorUIs.forEach(MonitorUI::displayUI);

        if (messageManager == null) {
            messageWindow.WriteMessage("Unable to register with message manager.");
            this.registered = false;
            return;
        }

        displayStartInformation();

        boolean done = false;
        int delay = 1000;

        while (!done) {
            MessageQueue messageQueue = getMessageQueue();
            if (messageQueue == null)
                break;

            try {
                for (int i = 0; i < messageQueue.GetSize(); i++) {
                    Message message = messageQueue.GetMessage();
                    messageHandlers.forEach(monitorMessageHandler -> {
                        if (monitorMessageHandler.canHandleMessageWithId(message.GetMessageId()))
                            monitorMessageHandler.handleMessage(message);
                    });
                }
            } catch (MonitorQuitSignal signal) {
                try {
                    messageManager.UnRegister();
                } catch (Exception ex) {
                    messageWindow.WriteMessage("Error unregistering: " + ex.getMessage());
                } finally {
                    done = true;
                    System.out.println("Shutting down...");
                    System.exit(1);
                }
            }

            try {
                Thread.sleep(delay);
            } catch (InterruptedException ex) {
                messageWindow.WriteMessage("System error:: " + ex.getMessage());
            }
        }
    }

    private void displayStartInformation() {
        messageWindow.WriteMessage("Registered with the message manager.");
        try {
            messageWindow.WriteMessage("   Participant id: " + messageManager.GetMyId());
            messageWindow.WriteMessage("   Registration Time: " + messageManager.GetRegistrationTime());
        } catch (Exception ex) {
            messageWindow.WriteMessage("Error:: " + ex.getMessage());
        }
    }

    private MessageQueue getMessageQueue() {
        try {
            return messageManager.GetMessageQueue();
        } catch (Exception e) {
            messageWindow.WriteMessage("Error getting message queue::" + e);
            return null;
        }
    }

    public List<MonitorUI> getMonitorUIs() {
        return monitorUIs;
    }

    public void setMonitorUIs(List<MonitorUI> monitorUIs) {
        this.monitorUIs = monitorUIs;
    }

    public List<MonitorMessageHandler> getMessageHandlers() {
        return messageHandlers;
    }

    public void setMessageHandlers(List<MonitorMessageHandler> messageHandlers) {
        this.messageHandlers = messageHandlers;
    }

    public boolean isRegistered() {
        return registered;
    }

    public MessageManagerInterface getMessageManager() {
        return messageManager;
    }

    public void setMessageManager(MessageManagerInterface messageManager) {
        this.messageManager = messageManager;
    }
}

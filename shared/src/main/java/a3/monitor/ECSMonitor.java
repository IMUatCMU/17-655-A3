package a3.monitor;

import a3.message.Message;
import a3.message.MessageManagerInterface;
import a3.message.MessageQueue;
import java.util.List;
import java.util.stream.IntStream;
import javax.annotation.Resource;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @since 1.0.0
 */
public class ECSMonitor extends Thread implements ApplicationContextAware {

    private List<MonitorUI> monitorUIs;
    private List<MonitorMessageHandler> messageHandlers;
    private ApplicationContext applicationContext;
    private MessageManagerInterface messageManager;
    private String messageManagerIP;
    private boolean registered = false;

    @Resource(name = "messageWindow")
    MessageWindow messageWindow;

    public ECSMonitor() {
        try {
            messageManager = new MessageManagerInterface();
        } catch (Exception e) {
            System.out.println("ECSMonitor::Error instantiating message manager interface: " + e);
            this.registered = false;
        }
    }

    public ECSMonitor(String MsgIpAddress) {
        messageManagerIP = MsgIpAddress;
        try {
            messageManager = new MessageManagerInterface(messageManagerIP);
        } catch (Exception e) {
            System.out.println("ECSMonitor::Error instantiating message manager interface: " + e);
            this.registered = false;
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void run() {
        this.monitorUIs.forEach(MonitorUI::displayUI);

        if (messageManager == null) {
            messageWindow.WriteMessage("Unable to register with message manager.");
            return;
        }

        displayStartInformation();

        boolean done = false;
        int delay = 1000;

        while (!done) {
            MessageQueue messageQueue = getMessageQueue();
            if (messageQueue == null)
                break;

            IntStream.of(messageQueue.GetSize()).forEach(value -> {
                Message message = messageQueue.GetMessage();
                messageHandlers.forEach(monitorMessageHandler -> {
                    if (monitorMessageHandler.canHandleMessageWithId(message.GetMessageId()))
                        monitorMessageHandler.handleMessage(message);
                });
            });
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
}

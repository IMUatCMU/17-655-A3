package a3.monitor;

import a3.message.Message;
import a3.message.MessageManagerInterface;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Weinan Qiu
 * @since 1.0.0
 */
public class QuitMonitorMessageHandler implements MonitorMessageHandler {

    @Autowired
    private MessageWindow messageWindow;

    @Autowired
    private MessageManagerInterface messageManager;

    private List<Indicator> indicators = new ArrayList<>();

    @Override
    public boolean canHandleMessageWithId(int id) {
        return 99 == id;
    }

    @Override
    public void handleMessage(Message message, Object context) {
        indicators.forEach(JFrame::dispose);
        messageWindow.WriteMessage("Simulation will stop.");
        throw new MonitorQuitSignal();
    }

    public List<Indicator> getIndicators() {
        return indicators;
    }

    public void setIndicators(List<Indicator> indicators) {
        this.indicators = indicators;
    }
}

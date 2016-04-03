package a3.intrusionSensor;

import a3.message.Message;
import a3.sensor.MessageResponder;

import static a3.intrusionSensor.IntrusionContext.*;

/**
 * {@link MessageResponder} implementation to simulate an intrusion event
 *
 * @author Weinan Qiu
 * @since 1.0.0
 */
public class BreakInSimulationResponder implements MessageResponder<IntrusionContext> {

    @Override
    public boolean canRespondToMessageWithId(int id) {
        return 7 == id;
    }

    @Override
    public IntrusionContext respondToMessage(Message message, Object object) {
        IntrusionContext context = (IntrusionContext) object;

        if ("W1".equalsIgnoreCase(message.GetMessage())) {
            context.setWindowStatus(BREAK_IN);
        } else if ("W0".equalsIgnoreCase(message.GetMessage())) {
            context.setWindowStatus(NORMAL);
        } else if ("D1".equalsIgnoreCase(message.GetMessage())) {
            context.setDoorStatus(BREAK_IN);
        } else if ("D0".equalsIgnoreCase(message.GetMessage())) {
            context.setDoorStatus(NORMAL);
        } else if ("M1".equalsIgnoreCase(message.GetMessage())) {
            context.setMotionStatus(BREAK_IN);
        } else if ("M0".equalsIgnoreCase(message.GetMessage())) {
            context.setMotionStatus(NORMAL);
        }

        return context;
    }
}

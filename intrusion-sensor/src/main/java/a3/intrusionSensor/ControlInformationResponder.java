package a3.intrusionSensor;

import a3.message.Message;
import a3.sensor.MessageResponder;

import static a3.intrusionSensor.IntrusionContext.ARMED;
import static a3.intrusionSensor.IntrusionContext.DISARMED;
import static a3.intrusionSensor.IntrusionContext.NORMAL;

/**
 * {@link MessageResponder} implementation to arm and disarm sensors.
 *
 * @author Weinan Qiu
 * @since 1.0.0
 */
public class ControlInformationResponder implements MessageResponder<IntrusionContext> {

    @Override
    public boolean canRespondToMessageWithId(int id) {
        return -6 == id;
    }

    @Override
    public IntrusionContext respondToMessage(Message message, Object object) {
        IntrusionContext context = (IntrusionContext) object;

        context.setDoorStatus(NORMAL);
        context.setWindowStatus(NORMAL);
        context.setMotionStatus(NORMAL);

        if ("W1".equalsIgnoreCase(message.GetMessage())) {
            context.setWindowArmed(ARMED);
        } else if ("W0".equalsIgnoreCase(message.GetMessage())) {
            context.setWindowArmed(DISARMED);
        } else if ("D1".equalsIgnoreCase(message.GetMessage())) {
            context.setDoorArmed(ARMED);
        } else if ("D0".equalsIgnoreCase(message.GetMessage())) {
            context.setDoorArmed(DISARMED);
        } else if ("M1".equalsIgnoreCase(message.GetMessage())) {
            context.setMotionArmed(ARMED);
        } else if ("M0".equalsIgnoreCase(message.GetMessage())) {
            context.setMotionArmed(DISARMED);
        }

        return context;
    }
}

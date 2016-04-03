package a3.intrusionSensor;

/**
 * State object for intrusion sensors
 *
 * @author Weinan Qiu
 * @since 1.0.0
 */
public class IntrusionContext {

    public static final boolean NORMAL = true;
    public static final boolean BREAK_IN = false;

    public static final boolean ARMED = true;
    public static final boolean DISARMED = false;

    private boolean windowStatus = NORMAL;
    private boolean doorStatus = NORMAL;
    private boolean motionStatus = NORMAL;

    private boolean windowArmed = ARMED;
    private boolean doorArmed = ARMED;
    private boolean motionArmed = ARMED;

    public boolean isWindowStatus() {
        return windowStatus;
    }

    public void setWindowStatus(boolean windowStatus) {
        this.windowStatus = windowStatus;
    }

    public boolean isDoorStatus() {
        return doorStatus;
    }

    public void setDoorStatus(boolean doorStatus) {
        this.doorStatus = doorStatus;
    }

    public boolean isMotionStatus() {
        return motionStatus;
    }

    public void setMotionStatus(boolean motionStatus) {
        this.motionStatus = motionStatus;
    }

    public boolean isWindowArmed() {
        return windowArmed;
    }

    public void setWindowArmed(boolean windowArmed) {
        this.windowArmed = windowArmed;
    }

    public boolean isDoorArmed() {
        return doorArmed;
    }

    public void setDoorArmed(boolean doorArmed) {
        this.doorArmed = doorArmed;
    }

    public boolean isMotionArmed() {
        return motionArmed;
    }

    public void setMotionArmed(boolean motionArmed) {
        this.motionArmed = motionArmed;
    }
}

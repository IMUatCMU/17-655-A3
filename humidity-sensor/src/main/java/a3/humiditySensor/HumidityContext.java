package a3.humiditySensor;

/**
 * @author Weinan Qiu
 * @since 1.0.0
 */
public class HumidityContext {

    private Float currentHumidity;
    private Float driftValue;
    private boolean humidifierState;
    private boolean dehumidiferState;

    public Float getCurrentHumidity() {
        return currentHumidity;
    }

    public void setCurrentHumidity(Float currentHumidity) {
        this.currentHumidity = currentHumidity;
    }

    public Float getDriftValue() {
        return driftValue;
    }

    public void setDriftValue(Float driftValue) {
        this.driftValue = driftValue;
    }

    public boolean isHumidifierState() {
        return humidifierState;
    }

    public void setHumidifierState(boolean humidifierState) {
        this.humidifierState = humidifierState;
    }

    public boolean isDehumidiferState() {
        return dehumidiferState;
    }

    public void setDehumidiferState(boolean dehumidiferState) {
        this.dehumidiferState = dehumidiferState;
    }
}

package a3.tempSensor;

/**
 * @author Weinan Qiu
 * @since 1.0.0
 */
public class TemperatureHandlingContext {

    private Float currentTemp;
    private Float drift;
    private boolean heaterState;
    private boolean chillerState;

    public Float getCurrentTemp() {
        return currentTemp;
    }

    public void setCurrentTemp(Float currentTemp) {
        this.currentTemp = currentTemp;
    }

    public Float getDrift() {
        return drift;
    }

    public void setDrift(Float drift) {
        this.drift = drift;
    }

    public boolean isHeaterState() {
        return heaterState;
    }

    public void setHeaterState(boolean heaterState) {
        this.heaterState = heaterState;
    }

    public boolean isChillerState() {
        return chillerState;
    }

    public void setChillerState(boolean chillerState) {
        this.chillerState = chillerState;
    }
}

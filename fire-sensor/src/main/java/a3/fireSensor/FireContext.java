package a3.fireSensor;

/**
 * @author Weinan Qiu
 * @since 1.0.0
 */
public class FireContext {

    private int fireLevel = 0;

    public int getFireLevel() {
        return fireLevel;
    }

    public void setFireLevel(int fireLevel) {
        if (fireLevel <= 0)
            this.fireLevel = 0;
        else
            this.fireLevel = fireLevel;
    }
}

package a3.assist;

import java.util.Random;

/**
 * @author Weinan Qiu
 * @since 1.0.0
 */
public class RandomHelper {

    public static boolean coinToss() {
        Random r = new Random();
        return (r.nextBoolean());
    }

    public static float getRandomNumber() {
        Random r = new Random();
        Float Val = -1.0f;
        while (Val < 0.1) {
            Val = r.nextFloat();
        }
        return Val;

    }
}

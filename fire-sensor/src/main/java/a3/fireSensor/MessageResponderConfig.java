package a3.fireSensor;

import a3.sensor.KillSignalResponder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Weinan Qiu
 * @since 1.0.0
 */
@Configuration
public class MessageResponderConfig {

    @Bean
    FireSimulationResponder fireSimulationResponder() {
        return new FireSimulationResponder();
    }

    @Bean
    SprinklerResponder sprinklerResponder() {
        return new SprinklerResponder();
    }

    @Bean
    KillSignalResponder killSignalResponder() {
        return new KillSignalResponder();
    }
}

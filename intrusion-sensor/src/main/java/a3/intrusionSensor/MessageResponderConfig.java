package a3.intrusionSensor;

import a3.sensor.KillSignalResponder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Application configuration for message responders
 *
 * @author Weinan Qiu
 * @since 1.0.0
 */
@Configuration
public class MessageResponderConfig {

    @Bean
    BreakInSimulationResponder breakInSimulationResponder() {
        return new BreakInSimulationResponder();
    }

    @Bean
    ControlInformationResponder controlInformationResponder() {
        return new ControlInformationResponder();
    }

    @Bean
    KillSignalResponder killSignalResponder() {
        return new KillSignalResponder();
    }
}

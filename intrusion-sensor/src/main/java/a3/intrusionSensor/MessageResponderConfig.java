package a3.intrusionSensor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
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
}

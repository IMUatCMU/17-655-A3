package a3.tempSensor;

import a3.sensor.KillSignalResponder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Weinan Qiu
 * @since 1.0.0
 */
@Configuration
public class MessageHandlerConfig {

    @Bean
    KillSignalResponder killSignalResponder() {
        return new KillSignalResponder();
    }

    @Bean
    HeaterChillerResponder heaterChillerResponder() {
        return new HeaterChillerResponder();
    }
}

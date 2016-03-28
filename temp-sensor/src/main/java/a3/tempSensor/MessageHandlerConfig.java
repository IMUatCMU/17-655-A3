package a3.tempSensor;

import a3.sensor.KillSignalResponder;
import a3.sensor.MessageResponder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Weinan Qiu
 * @since 1.0.0
 */
@Configuration
public class MessageHandlerConfig {

    @Bean
    MessageResponder killSignalResponder() {
        return new KillSignalResponder();
    }

    @Bean
    MessageResponder heaterChillerResponder() {
        return new HeaterChillerResponder();
    }
}

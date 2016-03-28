package a3.console;

import a3.monitor.HumidityMonitorMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;

/**
 * Humidity commands, execute a command like 'humid --low 10 --high 15'
 *
 * @since 1.0.0
 */
@Component
public class HumidityCommands implements CommandMarker {

    @Autowired
    private HumidityMonitorMessageHandler humidityMonitorMessageHandler;

    @CliCommand(value = "humid", help = "Set the humidity range")
    public String setTemperature(
            @CliOption(key = { "low" }, mandatory = true, help = "Set the lowest of humidity percentage") final Float humidityLow,
            @CliOption(key = { "high" }, mandatory = true, help = "Set the lowest of humidity percentage") final Float humidityHigh
    ) {
        if (humidityLow > humidityHigh)
            return "Lower value is greater than upper value!";

        humidityMonitorMessageHandler.setLow(humidityLow);
        humidityMonitorMessageHandler.setHigh(humidityHigh);

        return String.format("New humidity range set at %s to %s", humidityLow + "%", humidityHigh + "%");
    }
}

package a3.console;

import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.shell.core.annotation.CliOption;
import org.springframework.stereotype.Component;

/**
 * Temperature commands, execute a command like 'temp --low 50 --high 60'
 *
 * @since 1.0.0
 */
@Component
public class TemperatureCommands implements CommandMarker {

    @CliCommand(value = "temp", help = "Set the temperature range")
    public String setTemperature(
            @CliOption(key = { "low" }, mandatory = true, help = "Set the lowest of temperature") final Float tempLow,
            @CliOption(key = { "high" }, mandatory = true, help = "Set the lowest of temperature") final Float tempHigh
    ) {
        if (tempLow > tempHigh)
            return "Lower value is greater than upper value!";

        return String.format("New temperature range set at %s to %s", tempLow, tempHigh);
    }
}

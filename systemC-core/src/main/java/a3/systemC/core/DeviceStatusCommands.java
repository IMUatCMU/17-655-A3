package a3.systemC.core;

import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.core.CommandMarker;
import org.springframework.shell.core.annotation.CliCommand;
import org.springframework.stereotype.Component;

/**
 * Console commands to query device status
 *
 * @author Weinan Qiu
 * @since 1.0.0
 */
@Component
public class DeviceStatusCommands implements CommandMarker {

    @Autowired
    private DeviceRegistry deviceRegistry;

    @CliCommand(value = "device-status", help = "Query the status of connected devices")
    public String queryStatus() {
        String result = deviceRegistry.getAllDevices()
                .stream()
                .filter(RegisteredDevice::isOnline)
                .map(d -> "\t" + String.format("%s (%s)", d.getDeviceId(), d.getDescription()))
                .collect(Collectors.joining("\n"));
        return result;
    }
}

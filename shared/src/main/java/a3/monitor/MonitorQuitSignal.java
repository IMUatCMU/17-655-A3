package a3.monitor;

import a3.sensor.MessageResponder;

/**
 * Thrown when a {@link MonitorMessageHandler} or {@link MessageResponder} reads a shutdown signal (id = 99).
 * It can return the control to the calling class to deal with shutdown.
 *
 * @author Weinan Qiu
 * @since 1.0.0
 */
public class MonitorQuitSignal extends RuntimeException {
}

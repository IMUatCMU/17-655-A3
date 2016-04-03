package a3.console;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.shell.plugin.support.DefaultPromptProvider;
import org.springframework.stereotype.Component;

/**
 * Prompt name provider
 *
 * @author Weinan Qiu
 * @since 1.0.0
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class A3PromptProvider extends DefaultPromptProvider {

    @Override
    public String getPrompt() {
        return "ecs-console>";
    }
}

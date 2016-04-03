package a3.systemC;

import java.io.IOException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.shell.Bootstrap;
import org.springframework.stereotype.Component;

/**
 * System C
 *
 * @author Weinan Qiu
 * @since 1.0.0
 */
@Component
public class SystemC implements InitializingBean {

    public static void main(String[] args) throws IOException {
        Bootstrap.main(args);
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}

package a3.systemB;

import java.io.IOException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.shell.Bootstrap;
import org.springframework.stereotype.Component;

/**
 * System B
 *
 * @author Weinan Qiu
 * @since 1.0.0
 */
@Component
public class SystemB implements InitializingBean {

    public static void main(String[] args) throws IOException {
        Bootstrap.main(args);
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}

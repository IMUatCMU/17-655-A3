package a3.systemA;

import java.io.IOException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.shell.Bootstrap;
import org.springframework.stereotype.Component;

/**
 * @author Weinan Qiu
 * @since 1.0.0
 */
@Component
public class SystemA implements InitializingBean {

    public static void main(String[] args) throws IOException {
        Bootstrap.main(args);
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}

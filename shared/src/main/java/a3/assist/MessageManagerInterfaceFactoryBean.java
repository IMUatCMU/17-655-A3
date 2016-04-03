package a3.assist;

import a3.message.MessageManagerInterface;
import org.springframework.beans.factory.FactoryBean;

/**
 * Spring factory bean to create {@link MessageManagerInterface} based on whether property 'ip' is available.
 *
 * @author Weinan Qiu
 * @since 1.0.0
 */
public class MessageManagerInterfaceFactoryBean implements FactoryBean<MessageManagerInterface> {

    private final String ip;

    public MessageManagerInterfaceFactoryBean(String ip) {
        this.ip = ip;
    }

    @Override
    public MessageManagerInterface getObject() throws Exception {
        return "localhost".equals(ip) ?
                new MessageManagerInterface() :
                new MessageManagerInterface(ip);
    }

    @Override
    public Class<?> getObjectType() {
        return MessageManagerInterface.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}

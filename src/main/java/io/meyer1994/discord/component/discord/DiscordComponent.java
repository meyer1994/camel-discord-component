package io.meyer1994.discord.component.discord;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import org.apache.camel.Endpoint;
import org.apache.camel.spi.Metadata;
import org.apache.camel.spi.UriParam;
import org.apache.camel.spi.UriPath;
import org.apache.camel.spi.annotations.Component;
import org.apache.camel.support.DefaultComponent;

import java.util.Map;
import java.util.Set;

@Slf4j
@Getter
@Setter
@Component(value = "discord")
public class DiscordComponent extends DefaultComponent {
    @UriPath
    private String name;

    // Common
    @UriParam
    @Metadata(autowired = true)
    private JDA client;

    // Producer
    @UriParam
    private DiscordOperation operation = DiscordOperation.MESSAGE_SEND;

    // Consumer
    @UriParam
    private DiscordEvent event = DiscordEvent.ON_MESSAGE;


    @Override
    protected Endpoint createEndpoint(final String uri, final String remaining, final Map<String, Object> parameters) throws Exception {
        // Set props
        this.name = remaining;
        this.client = this.getClientFromCamelRegistry();

        final DiscordEndpoint endpoint = new DiscordEndpoint(uri, this);
        this.setProperties(endpoint, parameters);

        return endpoint;
    }

    protected JDA getClientFromCamelRegistry() {
        final Set<JDA> clientBeans = this.getCamelContext().getRegistry().findByType(JDA.class);
        
        if (clientBeans.isEmpty())
            throw new IllegalArgumentException("There should be one, and only one, JDA client");
        
        for (final JDA client : clientBeans)
            return client;
        
        throw new RuntimeException("Should not reach here");
    }
}

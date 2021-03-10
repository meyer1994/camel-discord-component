package io.meyer1994.discord.component.discord;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.spi.UriEndpoint;
import org.apache.camel.spi.UriParam;
import org.apache.camel.spi.UriPath;
import org.apache.camel.support.DefaultEndpoint;

@Data
@EqualsAndHashCode(callSuper = true)
@UriEndpoint(scheme = "discord", syntax = "discord:name", title = "Discord")
public class DiscordEndpoint extends DefaultEndpoint {
    @UriPath
    private String name;

    // Producer
    @UriParam
    private DiscordOperation operation = DiscordOperation.MESSAGE_SEND;

    // Consumer
    @UriParam
    private DiscordEvent event = DiscordEvent.ON_MESSAGE;


    public DiscordEndpoint(final String uri, final DiscordComponent component) {
        super(uri, component);
    }

    @Override
    public Producer createProducer() {
        return new DiscordProducer(this);
    }

    @Override
    public Consumer createConsumer(final Processor processor) {
        return new DiscordConsumer(this, processor);
    }

    @Override
    public DiscordComponent getComponent() {
        return (DiscordComponent) super.getComponent();
    }
}

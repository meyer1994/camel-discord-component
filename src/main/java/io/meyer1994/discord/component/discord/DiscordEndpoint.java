package io.meyer1994.discord.component.discord;

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.spi.UriEndpoint;
import org.apache.camel.support.DefaultEndpoint;

@UriEndpoint(scheme = "discord", syntax = "discord:name", title = "Discord")
public class DiscordEndpoint extends DefaultEndpoint {
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

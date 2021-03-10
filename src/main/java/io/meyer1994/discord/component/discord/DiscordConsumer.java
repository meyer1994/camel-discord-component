package io.meyer1994.discord.component.discord;

import io.meyer1994.discord.component.discord.handlers.MessageReceivedHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Processor;
import org.apache.camel.support.DefaultConsumer;


@Slf4j
public class DiscordConsumer extends DefaultConsumer {
    public DiscordConsumer(final DiscordEndpoint endpoint, final Processor processor) {
        super(endpoint, processor);
    }

    @Override
    public DiscordEndpoint getEndpoint() {
        return (DiscordEndpoint) super.getEndpoint();
    }

    @Override
    protected void doInit() throws Exception {
        super.doInit();

        final DiscordEvent event = this.getEndpoint().getEvent();
        if (event == DiscordEvent.ON_MESSAGE)
            this.getEndpoint()
                    .getComponent()
                    .getClient()
                    .addEventListener(new MessageReceivedHandler(this));
    }
}

package io.meyer1994.discord.component.discord.handlers;

import io.meyer1994.discord.component.discord.DiscordConstants;
import io.meyer1994.discord.component.discord.DiscordConsumer;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.apache.camel.Exchange;

public class MessageReceivedHandler extends BaseHandler {
    public MessageReceivedHandler(final DiscordConsumer consumer) {
        super(consumer);
    }

    @Override
    public void onMessageReceived(final MessageReceivedEvent event) {
        final Exchange exchange = this.getEndpoint().createExchange();

        exchange.getIn().setBody(event.getMessage().getContentDisplay());
        exchange.getIn().setHeader(DiscordConstants.CHANNEL_ID, event.getChannel().getId());
        exchange.getIn().setHeader(DiscordConstants.MESSAGE_ID, event.getMessage().getId());

        try {
            this.getConsumer().getProcessor().process(exchange);
        } catch (final Exception e) {
            exchange.setException(e);
        }
    }
}

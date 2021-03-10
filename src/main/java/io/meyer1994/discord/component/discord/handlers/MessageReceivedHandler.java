package io.meyer1994.discord.component.discord.handlers;

import io.meyer1994.discord.component.discord.DiscordConstants;
import io.meyer1994.discord.component.discord.DiscordConsumer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.apache.camel.Exchange;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MessageReceivedHandler extends ListenerAdapter {
    private DiscordConsumer consumer;

    @Override
    public void onMessageReceived(final MessageReceivedEvent event) {
        final Exchange exchange = this.consumer.getEndpoint().createExchange();

        exchange.getIn().setBody(event.getMessage());
        exchange.getIn().setHeader(DiscordConstants.CHANNEL_ID, event.getChannel().getId());
        exchange.getIn().setHeader(DiscordConstants.MESSAGE_ID, event.getMessage().getId());

        try {
            this.getConsumer().getProcessor().process(exchange);
        } catch (final Exception e) {
            exchange.setException(e);
        }
    }
}

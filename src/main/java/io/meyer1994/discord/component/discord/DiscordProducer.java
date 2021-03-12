package io.meyer1994.discord.component.discord;

import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import org.apache.camel.Exchange;
import org.apache.camel.support.DefaultProducer;

@Slf4j
public class DiscordProducer extends DefaultProducer {
    public DiscordProducer(final DiscordEndpoint endpoint) {
        super(endpoint);
    }

    @Override
    public DiscordEndpoint getEndpoint() {
        return (DiscordEndpoint) super.getEndpoint();
    }

    @Override
    public void process(final Exchange exchange) throws Exception {
        final DiscordOperation operation = this.getEndpoint().getOperation();
        switch (operation) {
            case MESSAGE_SEND:
                this.messageSend(exchange);
                return;
            case MESSAGE_REACT:
                this.messageReact(exchange);
                return;
            case MESSAGE_REPLY:
                this.messageReply(exchange);
                return;
        }
    }

    protected void messageReply(final Exchange exchange) {
        final String channelId = exchange.getIn().getHeader(DiscordConstants.CHANNEL_ID, String.class);
        final TextChannel channel = this.getEndpoint().getComponent().getClient().getTextChannelById(channelId);

        final String messageId = exchange.getIn().getHeader(DiscordConstants.MESSAGE_ID, String.class);
        final String reply = exchange.getIn().getBody(String.class);

        channel.retrieveMessageById(messageId)
                .queue(m -> m.reply(reply).queue());
    }
    
    protected void messageSend(final Exchange exchange) {
        final String channelId = exchange.getIn().getHeader(DiscordConstants.CHANNEL_ID, String.class);
        final TextChannel channel = this.getEndpoint().getComponent().getClient().getTextChannelById(channelId);

        final String message = exchange.getIn().getBody(String.class);
        channel.sendMessage(message).queue();
    }

    protected void messageReact(final Exchange exchange) {
        final String channelId = exchange.getIn().getHeader(DiscordConstants.CHANNEL_ID, String.class);
        final TextChannel channel = this.getEndpoint().getComponent().getClient().getTextChannelById(channelId);

        final String message = exchange.getIn().getHeader(DiscordConstants.MESSAGE_ID, String.class);
        final String emote = exchange.getIn().getBody(String.class);
        channel.addReactionById(message, emote).queue();
    }
}

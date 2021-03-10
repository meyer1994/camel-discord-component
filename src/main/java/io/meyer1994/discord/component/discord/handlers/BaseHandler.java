package io.meyer1994.discord.component.discord.handlers;

import io.meyer1994.discord.component.discord.DiscordConsumer;
import io.meyer1994.discord.component.discord.DiscordEndpoint;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.apache.camel.Exchange;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class BaseHandler extends ListenerAdapter {
    protected final DiscordConsumer consumer;

    protected DiscordEndpoint getEndpoint() {
        return this.consumer.getEndpoint();
    }
    
    protected void process(final Object object) {
        final Exchange exchange = this.consumer.getEndpoint().createExchange();
        exchange.getIn().setBody(object);
        try {
            this.consumer.getProcessor().process(exchange);
        } catch (final Exception e) {
            exchange.setException(e);
        }
    }
}

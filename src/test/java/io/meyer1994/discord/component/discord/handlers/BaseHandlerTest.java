package io.meyer1994.discord.component.discord.handlers;

import io.meyer1994.discord.component.discord.DiscordConsumer;
import io.meyer1994.discord.component.discord.DiscordEndpoint;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.support.DefaultExchange;
import org.apache.camel.support.DefaultMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;


public abstract class BaseHandlerTest<T extends BaseHandler> {
    @Mock protected DiscordConsumer consumer;
    @Mock protected DiscordEndpoint endpoint;
    @Mock protected Processor processor;

    protected T handler;

    protected static final String DATA = "nice";
    
    @BeforeEach
    public void setUpEndpoint() {
        final Exchange exchange = new DefaultExchange(new DefaultCamelContext());
        final Message message = new DefaultMessage(exchange);
        exchange.setIn(message);
        Mockito.doReturn(exchange).when(this.endpoint).createExchange();
    }

    @BeforeEach
    public void setUpConsumer() {
        Mockito.doReturn(this.endpoint).when(this.consumer).getEndpoint();
        Mockito.doReturn(this.processor).when(this.consumer).getProcessor();
    }

    @BeforeEach
    public void setUpHandler() {}

    @Test
    public void testProcess() throws Exception {
        this.handler.process(DATA);

        Mockito.verify(this.processor, Mockito.times(1)).process(any(Exchange.class));
    }
}

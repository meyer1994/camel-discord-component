package io.meyer1994.discord.component.discord;

import io.meyer1994.discord.component.discord.handlers.MessageReceivedHandler;
import net.dv8tion.jda.api.JDA;
import org.apache.camel.Processor;
import org.apache.camel.test.spring.junit5.CamelSpringTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;

@CamelSpringTest
public class DiscordConsumerTest {
    @Mock private Processor processor;
    @Mock private DiscordEndpoint endpoint;
    @Mock private JDA client;

    private DiscordConsumer consumer;

    @BeforeEach
    public void setUpEndpoint() {
        Mockito.doReturn(this.client).when(this.endpoint).getClient();
        Mockito.doReturn(DiscordEvent.ON_MESSAGE).when(this.endpoint).getEvent();
    }

    @BeforeEach
    public void setUpConsumer() throws Exception {
        this.consumer = new DiscordConsumer(this.endpoint, this.processor);
    }

    @Test
    public void testDoInit() throws Exception {
        this.consumer.doInit();

        Mockito.verify(this.endpoint, Mockito.times(1)).getEvent();
        Mockito.verify(this.endpoint, Mockito.times(1)).getClient();
        Mockito.verify(this.client, Mockito.times(1)).addEventListener(any(MessageReceivedHandler.class));
    }
}

package io.meyer1994.discord.component.discord;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.requests.RestAction;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.support.DefaultExchange;
import org.apache.camel.support.DefaultMessage;
import org.apache.camel.test.spring.junit5.CamelSpringTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@CamelSpringTest
public class DiscordProducerTest {
    @Mock private JDA client;
    @Mock private DiscordEndpoint endpoint;
    @Mock private TextChannel channel;
    @Mock private MessageAction messageAction;
    @Mock private RestAction<Void> emojiAction;

    private DiscordProducer producer;

    private static final String CHANNEL_ID = "123";
    private static final String EMOJI = "emoji";
    private static final String MESSAGE = "nice";
    private static final String MESSAGE_ID = "message_id";

    @BeforeEach
    public void setUpEndpoint() {
        Mockito.doReturn(CHANNEL_ID).when(this.endpoint).getChannel();
        Mockito.doReturn(this.client).when(this.endpoint).getClient();
    }
    
    @BeforeEach
    public void setUpClient() {
        Mockito.doReturn(this.channel).when(this.client).getTextChannelById(anyString());
    }
    
    @BeforeEach
    public void setUpChannel() {
        Mockito.doReturn(this.messageAction).when(this.channel).sendMessage(anyString());
        Mockito.doReturn(this.emojiAction).when(this.channel).addReactionById(anyString(), anyString());
    }

    @BeforeEach
    public void setUpProducer() {
        this.producer = new DiscordProducer(this.endpoint);
    }

    @Test
    public void testGetTextChannel() {
        final TextChannel channel = this.producer.getTextChannel();
        
        assertThat(channel).isSameAs(this.channel);
        Mockito.verify(this.endpoint, Mockito.times(1)).getClient();
        Mockito.verify(this.endpoint, Mockito.times(1)).getChannel();
        Mockito.verify(this.client, Mockito.times(1)).getTextChannelById(CHANNEL_ID);
    }

    @Test
    public void testProcess() throws Exception {
        this.producer = Mockito.spy(this.producer);
        Mockito.doNothing().when(this.producer).messageSend(any());
        Mockito.doReturn(DiscordOperation.MESSAGE_SEND).when(this.endpoint).getOperation();

        producer.process(null);

        Mockito.verify(producer, Mockito.times(1)).messageSend(any());
        Mockito.verify(this.endpoint, Mockito.times(1)).getOperation();
    }

    @Test
    public void testMessageSend() {
        final Exchange exchange = new DefaultExchange(new DefaultCamelContext());
        final Message message = new DefaultMessage(exchange);
        message.setBody(MESSAGE);
        exchange.setIn(message);

        this.producer.messageSend(exchange);

        Mockito.verify(this.channel, Mockito.times(1)).sendMessage(MESSAGE);
    }

    @Test
    public void testMessageReact() {
        final Exchange exchange = new DefaultExchange(new DefaultCamelContext());
        final Message message = new DefaultMessage(exchange);
        message.setBody(EMOJI);
        message.setHeader(DiscordConstants.MESSAGE_ID, MESSAGE_ID);
        exchange.setIn(message);

        this.producer.messageReact(exchange);

        Mockito.verify(this.channel, Mockito.times(1)).addReactionById(MESSAGE_ID, EMOJI);
    }
}

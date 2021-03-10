package io.meyer1994.discord.component.discord.handlers;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.apache.camel.test.spring.junit5.CamelSpringTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;

@CamelSpringTest
public class MessageReceivedHandlerTest extends BaseHandlerTest<MessageReceivedHandler> {
    private static final String MESSAGE = "nice";
    private static final String REGEX = "\\d+";
    
    @Override
    @BeforeEach
    public void setUpEndpoint() {
        super.setUpEndpoint();
        Mockito.doReturn(REGEX).when(this.endpoint).getRegex();
    }

    @Override
    @BeforeEach
    public void setUpHandler() {
        this.handler = new MessageReceivedHandler(this.consumer);
        this.handler = Mockito.spy(this.handler);
    }
    
    @Test
    public void testMatchRegex() {
        final Message message = Mockito.mock(Message.class);
        Mockito.doReturn(MESSAGE).when(message).getContentStripped();
        final MessageReceivedEvent event = new MessageReceivedEvent(null, 0L, message);

        final Boolean falseResult = this.handler.matchRegex(event);

        assertThat(falseResult).isFalse();
    }
}

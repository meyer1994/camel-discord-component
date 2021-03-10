package io.meyer1994.discord.component.discord;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.test.spring.junit5.CamelSpringTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@CamelSpringTest
public class DiscordComponentTest {
    private CamelContext context;
    private DiscordComponent component;

    @BeforeEach
    public void setUp() {
        this.context = new DefaultCamelContext();
        this.component = new DiscordComponent();
        this.context.addComponent("discord", this.component);
    }

    @Test
    public void testCreateEndpoint() throws Exception {
        final DiscordEndpoint endpoint = (DiscordEndpoint) this.component.createEndpoint("discord:123?regex=nice");

        assertThat(endpoint.getChannel()).isEqualTo("123");
        assertThat(endpoint.getRegex()).isEqualTo("nice");
        assertThat(endpoint.getEvent()).isEqualTo(DiscordEvent.ON_MESSAGE);
        assertThat(endpoint.getOperation()).isEqualTo(DiscordOperation.MESSAGE_SEND);
    }
}

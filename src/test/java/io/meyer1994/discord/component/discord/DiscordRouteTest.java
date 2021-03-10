package io.meyer1994.discord.component.discord;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.test.spring.junit5.CamelSpringTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

@Slf4j
@CamelSpringTest
@ContextConfiguration(classes = DiscordTestConfig.class)
public class DiscordRouteTest {
    @Autowired
    private CamelContext context;
    @Autowired
    private ProducerTemplate producer;

    @Test
    public void testRouteProducer() throws Exception {
        log.info(this.context.getRoutes().toString());
        this.producer.sendBody("direct:start", "nice");
    }
}

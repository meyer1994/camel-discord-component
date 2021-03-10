package io.meyer1994.discord;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class BotRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("discord:ping")
                .filter().simple("${body} == '!ping'")
                .log("Pong!")
                .transform().constant("Pong!")
                .to("discord:pong");
    }
}

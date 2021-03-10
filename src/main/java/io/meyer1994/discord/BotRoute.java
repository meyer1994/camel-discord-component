package io.meyer1994.discord;

import io.meyer1994.discord.component.discord.DiscordConstants;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BotRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("discord:ping")
                .filter().simple("${body.contentRaw} == '!ping'")
                .log("Pong")
                .transform().constant("Pong!")
                .to("discord:pong");

        from("discord:nice")
            .filter().simple("${body.contentRaw} == '!nice'")
            .filter().simple("${body.referencedMessage} != null")
            .log("Nice")
            .setHeader(DiscordConstants.MESSAGE_ID).simple("${body.referencedMessage.id}")
            .transform().constant(List.of("U+1F1F3", "U+1F1EE", "U+1F1E8", "U+1F1EA"))  // NICE
            .split().body()
                .to("discord:react?operation=MESSAGE_REACT");
    }
}

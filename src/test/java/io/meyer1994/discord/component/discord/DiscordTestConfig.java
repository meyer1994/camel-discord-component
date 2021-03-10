package io.meyer1994.discord.component.discord;

import org.apache.camel.CamelContext;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DiscordTestConfig {
    @Bean
    public CamelContext context() throws Exception {
        final RoutesBuilder routes = new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:start")
                        .to("mock:result");
            }
        };
        
        final CamelContext context = new DefaultCamelContext();
        context.addRoutes(routes);

        return context;
    }
}

package io.meyer1994.discord;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.security.auth.login.LoginException;

@Configuration
public class JdaConfig {
    private static final String TOKEN = "TOKEN";
    
    @Bean
    public JDA jda() throws LoginException {
        return JDABuilder.createDefault(TOKEN)
                .build();
    }
}

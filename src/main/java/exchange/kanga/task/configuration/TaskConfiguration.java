package exchange.kanga.task.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jwt.BadJwtException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.server.resource.web.DefaultBearerTokenResolver;
import org.springframework.security.web.SecurityFilterChain;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;

import static java.util.Map.entry;

@Configuration
@EnableWebSecurity
public class TaskConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        DefaultBearerTokenResolver resolver = new DefaultBearerTokenResolver();
        resolver.setAllowFormEncodedBodyParameter(true);
        http.authorizeHttpRequests(auth-> auth.anyRequest().authenticated())
                .csrf(AbstractHttpConfigurer::disable).oauth2ResourceServer(oauth->oauth
                        .bearerTokenResolver(resolver).jwt(Customizer.withDefaults()));
        return http.build();

    }

    //dummy code for oauth

    @Bean
    public JwtDecoder decoder(){
        return  new JwtDecoder() {
            @Override
            public Jwt decode(String token) throws JwtException {
                if (token.equals("ABC123")) {
                    return new Jwt(token, Instant.now(), Instant.now().plus(1, ChronoUnit.HOURS),
                            Map.ofEntries(
                                    entry("alg", "HS256"),
                                    entry("type", "JWT")
                            ),
                            Map.ofEntries(
                                    entry("sub", "kanga")
                            ));
                }  else {
                    throw new BadJwtException("fake token was to fake");
                }
            }
        };
    }



}
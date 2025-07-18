package exchange.kanga.task.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
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
    BearerTokenResolver bearerTokenResolver() {
        DefaultBearerTokenResolver bearerTokenResolver = new DefaultBearerTokenResolver();
        bearerTokenResolver.setBearerTokenHeaderName(HttpHeaders.PROXY_AUTHORIZATION);
        return bearerTokenResolver;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        DefaultBearerTokenResolver resolver = new DefaultBearerTokenResolver();
        resolver.setAllowFormEncodedBodyParameter(true);
        http.authorizeHttpRequests(auth-> auth.anyRequest().permitAll())
                .csrf(AbstractHttpConfigurer::disable).oauth2ResourceServer(oauth->oauth
                        .bearerTokenResolver(resolver).jwt(Customizer.withDefaults()));
        return http.build();

    }

    @Bean
    public JwtDecoder decoder(){
        return  new JwtDecoder() {
            @Override
            public Jwt decode(String token) throws JwtException {
                return new Jwt("t", Instant.now(), Instant.now().plus(1, ChronoUnit.HOURS),
                        Map.ofEntries(
                                entry("alg", "HS256"),
                                entry("type", "JWT")
                        ) ,
                        Map.ofEntries(
                                entry("sub", "kanga"),
                                entry("admin", true)
                        )
                        );
            }
        };
    }



}
package exchange.kanga.task.configuration;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "kanga.api")
@Getter
@Setter
public class Kanga {
    String url;
}

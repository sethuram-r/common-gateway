package smartshare.newcommongateway.configurations;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "client")
public @Data
class ClientConfiguration {

    private String uri;
}

package smartshare.newcommongateway.configurations;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "routes")
public @Data
class ServerConfigurations {
    private List<Server> servers = new ArrayList<>();
}


package smartshare.newcommongateway.configurations;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;


@Configuration
public class CommonGatewayConfiguration {


    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


}

package smartshare.newcommongateway.configurations;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class CommonGatewayConfiguration {

    @Value("${client.host}")
    private String clientHostAddress;
    @Value("${client.port}")
    private String clientPort;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping( "/administrationserver/**" ).allowedOrigins( "http://localhost:4200" )
                        .allowedMethods( "GET", "POST", "DELETE", "PUT" );
                registry.addMapping( "/coreserver/**" ).allowedOrigins( "http://localhost:4200" )
                        .allowedMethods( "GET", "POST", "DELETE", "PUT" );
                registry.addMapping( "/lockserver/**" ).allowedOrigins( "http://" + clientHostAddress + ":" + clientPort )
                        .allowedMethods( "GET", "POST", "DELETE", "PUT" );
            }
        };
    }
}

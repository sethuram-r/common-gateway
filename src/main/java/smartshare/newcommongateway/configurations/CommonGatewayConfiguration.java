package smartshare.newcommongateway.configurations;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
public class CommonGatewayConfiguration {


    private final ClientConfiguration clientConfiguration;

    @Autowired
    public CommonGatewayConfiguration(ClientConfiguration clientConfiguration) {
        this.clientConfiguration = clientConfiguration;
    }


    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping( "/administrationserver/**" ).allowedOrigins( clientConfiguration.getUri() )
                        .allowedMethods( "GET", "POST", "DELETE", "PUT" );
                registry.addMapping( "/coreserver/**" ).allowedOrigins( clientConfiguration.getUri() )
                        .allowedMethods( "GET", "POST", "DELETE", "PUT" );
                registry.addMapping( "/lockserver/**" ).allowedOrigins( clientConfiguration.getUri() )
                        .allowedMethods( "GET", "POST", "DELETE", "PUT" );
            }
        };
    }
}

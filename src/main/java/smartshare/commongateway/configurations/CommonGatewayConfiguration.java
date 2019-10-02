package smartshare.commongateway.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import smartshare.commongateway.filter.AddResponseHeaderFilter;
import smartshare.commongateway.services.JWTService;


@Configuration
public class CommonGatewayConfiguration {

    @Value("${client.host}")
    private String clientHostAddress;
    @Value("${client.port}")
    private String clientPort;
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/authenticate/**").allowedOrigins("http://" + clientHostAddress + ":" + clientPort);
                registry.addMapping("/authenticate/signIn").allowedOrigins("http://" + clientHostAddress + ":" + clientPort)
                        .allowCredentials(Boolean.TRUE).exposedHeaders(JWTService.TOKEN_NAME).allowedHeaders(JWTService.TOKEN_NAME);
                registry.addMapping("/file-service/*").allowedOrigins("http://" + clientHostAddress + ":" + clientPort)
                        .allowCredentials(Boolean.TRUE).exposedHeaders(JWTService.TOKEN_NAME).allowedHeaders(JWTService.TOKEN_NAME);
            }
        };
    }

    @Bean(name = "AddResponseHeaderFilter")
    public FilterRegistrationBean<AddResponseHeaderFilter> addResponseHeaderFilter() {
        FilterRegistrationBean<AddResponseHeaderFilter> responseHeaderBean = new FilterRegistrationBean<>();
        responseHeaderBean.setFilter(new AddResponseHeaderFilter());
        responseHeaderBean.addUrlPatterns("/file-service/**");
        return responseHeaderBean;
    }

}

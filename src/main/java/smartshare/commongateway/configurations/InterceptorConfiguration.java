package smartshare.commongateway.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import smartshare.commongateway.interceptor.RequestSourceCheckerInterceptor;


@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {

    @Autowired
    private RequestSourceCheckerInterceptor requestSourceCheckerInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestSourceCheckerInterceptor);
    }
}

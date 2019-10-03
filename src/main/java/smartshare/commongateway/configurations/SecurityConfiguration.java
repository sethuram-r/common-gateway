package smartshare.commongateway.configurations;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;
import smartshare.commongateway.filter.ExceptionHandlerFilter;
import smartshare.commongateway.filter.JwtRequestFilter;
import smartshare.commongateway.handler.JwtAuthenticationEntryPoint;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    JwtRequestFilter jwtRequestFilter;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private ExceptionHandlerFilter exceptionHandlerFilter;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        //security.httpBasic().disable();
        httpSecurity.csrf().disable();
        httpSecurity.cors();
        httpSecurity.authorizeRequests().antMatchers("/authenticate/**").permitAll()
                .and().authorizeRequests().antMatchers("/file-service/**").authenticated()
                .and().exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(exceptionHandlerFilter, CorsFilter.class);

    }
}

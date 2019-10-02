package smartshare.commongateway.configurations;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import smartshare.commongateway.filter.JwtRequestFilter;
import smartshare.commongateway.handler.JwtAuthenticationEntryPoint;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    JwtRequestFilter jwtRequestFilter;

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        //security.httpBasic().disable();
        httpSecurity.csrf().disable();
        httpSecurity.cors();
        httpSecurity.authorizeRequests().antMatchers("/authenticate/**").permitAll()
                .and().authorizeRequests().antMatchers("/file-service/**").authenticated()
                .and().exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

    }

    //    @Override
//    protected void configure(HttpSecurity http) throws Exception{
//        //http.authorizeRequests().and().requiresChannel().antMatchers().requiresSecure();
//        http.csrf().disable();
//        // dont authenticate this particular request
//        http.authorizeRequests().antMatchers("/authenticate/signIn").permitAll();
////                .and().authorizeRequests()
//                // all other requests need to be authenticated
////        .anyRequest().authenticated().and().exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
////                // make sure we use stateless session; session won't be used to store user's state.
////                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
////        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
//    }
}

package smartshare.commongateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import smartshare.commongateway.exception.TokenNotFoundException;
import smartshare.commongateway.services.JWTService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
public class AddResponseHeaderFilter implements Filter {

    @Autowired
    JWTService jwtService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("Inside AddResponseHeaderFilter");
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        String tokenInRequestHeader = httpServletRequest.getHeader(JWTService.TOKEN_NAME);
        if (tokenInRequestHeader != null) {
            httpServletResponse.setHeader(JWTService.TOKEN_NAME, tokenInRequestHeader);
        } else {
            throw new TokenNotFoundException();
        }
        filterChain.doFilter(servletRequest, httpServletResponse);
    }
}

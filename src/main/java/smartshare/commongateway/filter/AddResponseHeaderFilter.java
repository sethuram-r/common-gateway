package smartshare.commongateway.filter;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import smartshare.commongateway.services.JWTService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
public class AddResponseHeaderFilter implements Filter {

    private static final String RESOURCE_PATH_TO_EXCLUDE = "/authenticate";

    @Autowired
    JWTService jwtService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("Inside AddResponseHeaderFilter");

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        System.out.println("httpServletRequest--getServletPath-------->" + httpServletRequest.getServletPath());
        System.out.println("getHttpServletMapping---------->" + httpServletRequest.getHttpServletMapping());
        if (!(httpServletRequest.getServletPath().contains(RESOURCE_PATH_TO_EXCLUDE))) {   // this can be removed as the url matcher given in configuration eliminates this need
            String tokenInRequestHeader = httpServletRequest.getHeader(JWTService.TOKEN_NAME);
            System.out.println("httpServletRequest---------->" + tokenInRequestHeader);
            if (tokenInRequestHeader != null) {
                httpServletResponse.setHeader(
                        JWTService.TOKEN_NAME, tokenInRequestHeader);
            }
        }
        System.out.println("after adding----token--->" + httpServletResponse.getHeader(JWTService.TOKEN_NAME));
        filterChain.doFilter(servletRequest, httpServletResponse);

    }
}

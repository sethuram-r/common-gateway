package smartshare.commongateway.filter;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import smartshare.commongateway.model.CustomUserDetail;
import smartshare.commongateway.services.JWTService;
import smartshare.commongateway.services.UserSessionDetailsService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private static final String RESOURCE_PATH_TO_EXCLUDE = "/authenticate";

    @Autowired
    private UserSessionDetailsService jwtUserDetailsService;

    @Autowired
    private JWTService jwtService;

//    private Boolean checkUrlToBeProcessedInExcludedUrlsList(String urlToBeProcessed){
//        for (UrlsNotToBeAddedWithJwtToken url: UrlsNotToBeAddedWithJwtToken.values())
//            if (urlToBeProcessed.contains(url.getUrlsActualRepresentation())) {
//                return Boolean.TRUE;
//            }
//        return Boolean.FALSE;
//    }

    @Override
    protected void doFilterInternal
            (HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain)
            throws ServletException, IOException {
        log.info("Inside JwtFilter");
        httpServletRequest.getServletPath();
        System.out.println("url---------->" + httpServletRequest.getRequestURL().toString());
        if (!(httpServletRequest.getServletPath().contains(RESOURCE_PATH_TO_EXCLUDE))) {

//        if (!checkUrlToBeProcessedInExcludedUrlsList(httpServletRequest.getRequestURL().toString())) {
            System.out.println("httpServletRequest--------->" + httpServletRequest.getHeader(JWTService.TOKEN_NAME));
            final String requestTokenHeader = httpServletRequest.getHeader(JWTService.TOKEN_NAME);

            String userName = null;
            String jwtToken = null;

            // JWT Token is in the form "Bearer token". Remove Bearer word and get

            if (requestTokenHeader != null && requestTokenHeader.startsWith(JWTService.TOKEN_PREFIX)) {
                jwtToken = requestTokenHeader.substring(6);
                try {
                    userName = jwtService.getUsernameFromToken(jwtToken);
                    System.out.println("userName------------" + userName);
                } catch (IllegalArgumentException e) {
                    System.out.println("Unable to get JWT Token");
                } catch (ExpiredJwtException e) {
                    System.out.println("JWT Token has expired");
                }
            } else {
                log.warn("JWT Token does not begin with Bearer String");
            }

            System.out.println("seurity context-----" + SecurityContextHolder.getContext().getAuthentication());
            // Once we get the token validate it.
            if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                System.out.println("inside-------2-----");
                CustomUserDetail userDetails = this.jwtUserDetailsService.loadUserByUsername(userName);

                System.out.println("userDetails-------" + userDetails);

                // if token is valid configure Spring Security to manually set authentication

                if (jwtService.validateToken(jwtToken, userDetails)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

                    // After setting the Authentication in the context, we specify that the current user is authenticated. So it passes the
                    // Spring Security Configurations successfully.
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    System.out.println("security passed-------");
                }
            }

        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);

    }
}

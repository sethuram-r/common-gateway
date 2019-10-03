package smartshare.commongateway.filter;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import smartshare.commongateway.constant.ResourcePathToBeExcluded;
import smartshare.commongateway.exception.ExpiredJwtTokenException;
import smartshare.commongateway.exception.InvalidJwtTokenException;
import smartshare.commongateway.exception.TokenNotFoundException;
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

    @Override
    protected void doFilterInternal
            (HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain)
            throws ServletException, IOException, ExpiredJwtTokenException, TokenNotFoundException {
        log.info("Inside JwtFilter");
        String userNameFromToken;
        String jwtTokenPresentInRequest;

        if (!(httpServletRequest.getServletPath().contains(ResourcePathToBeExcluded.AUTHENTICATE.getUrlsActualRepresentation()))) {
            final String authenticationTokenHeader = httpServletRequest.getHeader(JWTService.TOKEN_NAME);
            if (null != authenticationTokenHeader && authenticationTokenHeader.startsWith(JWTService.TOKEN_PREFIX)) {
                // JWT Token is in the form "Bearer token". Remove Bearer word and get
                jwtTokenPresentInRequest = authenticationTokenHeader.substring(JWTService.TOKEN_PREFIX.length());
                try {
                    userNameFromToken = jwtService.getUsernameFromToken(jwtTokenPresentInRequest);
                    if (userNameFromToken == null) throw new InvalidJwtTokenException();
                } catch (ExpiredJwtException e) {
                    throw new ExpiredJwtTokenException();
                } catch (Exception e) {
                    throw new InvalidJwtTokenException();
                }
            } else {
                throw new TokenNotFoundException();

            }

            /* Check if the user is authenticated already with spring security context in the form of principal object */

            if (null == SecurityContextHolder.getContext().getAuthentication()) {
                CustomUserDetail userDetails = this.jwtUserDetailsService.loadUserByUsername(userNameFromToken);

                // if token is valid configure Spring Security to manually set authentication
                if (jwtService.validateToken(jwtTokenPresentInRequest, userDetails)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

                    // After setting the Authentication in the context, we specify that the current user is authenticated. So it passes the
                    // Spring Security Configurations successfully.
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                } else {
                    throw new InvalidJwtTokenException();
                }
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);

    }
}

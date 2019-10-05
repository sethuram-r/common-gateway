package smartshare.commongateway.filter;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import smartshare.commongateway.exception.ExpiredJwtTokenException;
import smartshare.commongateway.exception.InvalidJwtTokenException;
import smartshare.commongateway.exception.TokenNotFoundException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ExceptionHandlerFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (TokenNotFoundException exception) {
            response.sendError(HttpStatus.PRECONDITION_REQUIRED.value(), exception.getMessage());
        } catch (InvalidJwtTokenException exception) {
            response.sendError(HttpStatus.PRECONDITION_FAILED.value(), exception.getMessage());
        } catch (ExpiredJwtTokenException exception) {
            response.sendError(HttpStatus.PRECONDITION_FAILED.value(), exception.getMessage());
        }
    }
}

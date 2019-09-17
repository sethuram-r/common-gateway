package smartshare.commongateway.interceptor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import smartshare.commongateway.exception.InvalidHostAddressException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class RequestSourceCheckerInterceptor extends HandlerInterceptorAdapter {

    @Value("${client.host}")
    private String clientHostAddress;
    @Value("${client.port}")
    private String clientPort;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (!(request.getHeader("origin").contains(clientHostAddress + ":" + clientPort))) {
            throw new InvalidHostAddressException("UnAuthorized Host");
        }
        return super.preHandle(request, response, handler);
    }

}

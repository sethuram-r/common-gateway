package smartshare.commongateway.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import smartshare.commongateway.model.redis.UserSessionDetail;

import java.util.Map;
import java.util.Objects;

@Slf4j
@Component
public class SignInRequestHandler extends RequestHandler {


    private UserSessionDetailsService userSessionDetailsService;
    private JWTService jwtService;

    @Autowired
    public SignInRequestHandler(RestTemplate restTemplate, UserSessionDetailsService userSessionDetailsService, JWTService jwtService) {
        this.restTemplate = restTemplate;
        this.userSessionDetailsService = userSessionDetailsService;
        this.jwtService = jwtService;
    }

    @Override
    ResponseEntity<Object> processRequest(RequestEntity requestEntityToBeProcessed) {
        log.info( "Inside SignInRequestHandler" );
        String urlToSignInService = "http://" + authenticationServerHostName + ":" + authenticationServerPort + "/signIn";
        ResponseEntity<Object> responseEntityFromAuthenticationService = restTemplate.postForEntity( urlToSignInService, requestEntityToBeProcessed.getBody(), Object.class );
        Map responseEntityFromAuthenticationServiceInMap = (Map) Objects.requireNonNull( responseEntityFromAuthenticationService.getBody() );
        UserSessionDetail userSessionDetailsToBePersisted = new UserSessionDetail( responseEntityFromAuthenticationServiceInMap );
        try {
            userSessionDetailsService.saveUserSessionDetail( userSessionDetailsToBePersisted );
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set( "Access-Control-Expose-Headers", JWTService.TOKEN_NAME );
            responseHeaders.set( JWTService.TOKEN_NAME, jwtService.generateToken( responseEntityFromAuthenticationServiceInMap ) );
            return ResponseEntity.ok().headers( responseHeaders ).body( responseEntityFromAuthenticationServiceInMap.keySet().removeIf( key -> key == "sessionId" ) );
        } catch (Exception exception) {
            smartshare.newcommongateway.model.Status status = new smartshare.newcommongateway.model.Status( exception.getMessage().concat( String.valueOf( exception.getCause() ) ) );
            return new ResponseEntity<>( status, HttpStatus.INTERNAL_SERVER_ERROR );
        }
    }


}



package smartshare.commongateway.services;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;


@Slf4j
@Component
public class SignUpRequestHandler extends RequestHandler {


    @Override
    public ResponseEntity<Object> processRequest(RequestEntity requestEntityToBeProcessed) {
        log.info( "Inside SignUpRequestHandler" );
        String urlToSignUpService = "http://" + this.authenticationServerHostName + ":" + this.authenticationServerPort + "/signUp";
        try {
            return restTemplate.postForEntity( urlToSignUpService, requestEntityToBeProcessed.getBody(), Object.class );
        } catch (final HttpClientErrorException httpClientErrorException) {
            smartshare.newcommongateway.model.Status status = new smartshare.newcommongateway.model.Status( httpClientErrorException.getResponseBodyAsString() );
            return new ResponseEntity<>( status, httpClientErrorException.getStatusCode() );
        }
    }
}


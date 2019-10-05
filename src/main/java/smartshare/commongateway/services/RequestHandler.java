package smartshare.commongateway.services;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


public abstract class RequestHandler {

    @Value("${authenticationServer.host}")
    String authenticationServerHostName;
    @Value("${authenticationServer.port}")
    String authenticationServerPort;

    RestTemplate restTemplate;

    abstract ResponseEntity<Object> processRequest(RequestEntity requestEntityToBeProcessed);

}

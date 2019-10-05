package smartshare.commongateway.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Slf4j
@Component
public class StubHandler extends RequestHandler {

    @Autowired
    public StubHandler(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @Override
    ResponseEntity<Object> processRequest(RequestEntity requestEntity) {
        log.info( "Inside stub" );
        String urlToStub = "http://" + this.authenticationServerHostName + ":" + this.authenticationServerPort + "/stub";
        return restTemplate.getForEntity( urlToStub, Object.class );
    }
}

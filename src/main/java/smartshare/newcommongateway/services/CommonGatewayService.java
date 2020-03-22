package smartshare.newcommongateway.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import smartshare.newcommongateway.configurations.ServerConfigurations;

import java.util.Objects;


@Slf4j
@Service
public class CommonGatewayService {


    private RestTemplate restTemplate;
    private ServerConfigurations serverConfigurations;

    @Autowired
    CommonGatewayService(RestTemplate restTemplate, ServerConfigurations serverConfigurations) {
        this.restTemplate = restTemplate;
        this.serverConfigurations = serverConfigurations;
    }

    private String identifyServer(String uri) {

        String applicationName = uri.split( "/" )[1];
        return serverConfigurations.getServers().stream()
                .filter( server -> server.getName().equals( applicationName ) )
                .findFirst()
                .map( server -> server.getUri() + uri.replace( "/" + applicationName, "" ) )
                .orElse( null );
    }

    public ResponseEntity forwardRequests(RequestEntity request) {
        log.info( "Inside forwardRequests" );

        try {
            String resolvedServerPath = Objects.requireNonNull( identifyServer( request.getUrl().getPath() ) );
            if (request.getMethod().equals( HttpMethod.GET ))
                resolvedServerPath = resolvedServerPath + "?" + request.getUrl().getQuery();
            return restTemplate
                    .exchange( resolvedServerPath, request.getMethod(), request, Object.class );
        } catch (NullPointerException e) {
            return ResponseEntity.notFound().build();
        }
    }


}


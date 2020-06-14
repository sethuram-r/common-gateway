package smartshare.newcommongateway.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import smartshare.newcommongateway.configurations.ServerConfigurations;

import java.util.Objects;


@Slf4j
@Service
public class CommonGatewayService {


    private final RestTemplate restTemplate;
    private final ServerConfigurations serverConfigurations;

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
            if (request.getMethod().equals( HttpMethod.GET ) || request.getMethod().equals( HttpMethod.DELETE )) {
                if (request.getUrl().getQuery() != null) {
                    resolvedServerPath = resolvedServerPath + "?" + request.getUrl().getQuery();
                }


                if (request.getUrl().getPath().contains( "file" )) {
                    return restTemplate
                            .exchange( resolvedServerPath, request.getMethod(), request, Resource.class );
                }
            }

            return restTemplate.exchange( resolvedServerPath, request.getMethod(), request, Object.class );

        } catch (RestClientException e) {
            log.error( "Exception  " + e.getMessage() );
            if (Objects.requireNonNull( e.getMessage() ).contains( "401" ))
                return new ResponseEntity<>( HttpStatus.UNAUTHORIZED );
            return new ResponseEntity<>( HttpStatus.PRECONDITION_FAILED );
        } catch (NullPointerException e) {
            return ResponseEntity.notFound().build();
        }
    }


}


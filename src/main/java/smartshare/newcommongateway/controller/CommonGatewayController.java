package smartshare.newcommongateway.controller;


import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import smartshare.newcommongateway.services.CommonGatewayService;


@Slf4j
@RestController
@RequestMapping(path = "/*/", produces = "application/json")
public class CommonGatewayController {

    private final CommonGatewayService service;
    private final MeterRegistry registry;

    @Autowired
    public CommonGatewayController(CommonGatewayService service, MeterRegistry registry) {
        this.service = service;
        this.registry = registry;
    }

    @RequestMapping(
            value = "**",
            method = {RequestMethod.DELETE, RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT}
    )
    public ResponseEntity forwardRequests(RequestEntity request) {
        log.info( "In forwardRequests" );
        final ResponseEntity responseEntity = service.forwardRequests( request );
        registry.counter( "incoming.gateway.response",
                "path", request.getUrl().getPath(),
                "method", request.getMethod().toString(),
                "status", String.valueOf( responseEntity.getStatusCodeValue() )
        ).increment();
        return responseEntity;
    }

}





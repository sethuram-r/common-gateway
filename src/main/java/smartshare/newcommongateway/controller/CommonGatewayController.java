package smartshare.newcommongateway.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import smartshare.newcommongateway.services.CommonGatewayService;


@Slf4j
@RestController
@RequestMapping(path = "/*/", produces = "application/json")
public class CommonGatewayController {

    @Autowired
    CommonGatewayService service;

    @GetMapping(value = "/hello")
    public String defaultMethod(RequestEntity request) {
        return "Hello !";
    }


    @RequestMapping(
            value = "**",
            method = {RequestMethod.PUT, RequestMethod.POST, RequestMethod.GET, RequestMethod.DELETE}
    )
    public ResponseEntity forwardRequests(RequestEntity request) {
        log.info( "In forwardRequests" );
        return service.forwardRequests( request );
    }

}





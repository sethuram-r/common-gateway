package smartshare.commongateway.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import smartshare.commongateway.services.CommonGatewayService;


@Slf4j
@RestController
@RequestMapping(path = "/",produces = "application/json")
public class CommonGatewayController {

    @Autowired
    CommonGatewayService service;

    @GetMapping(value = "/")
    public String defaultMethod(RequestEntity request) {
        return "Hello !";
    }


    @RequestMapping(
            value = "**",
            method = {RequestMethod.GET, RequestMethod.POST}
    )
    public ResponseEntity forwardPostRequestsToServiceHandlers(RequestEntity request) {
        log.info("In forwardPostRequestsToRespectiveServiceHandlers");
        return service.forwardPostRequestsToRespectiveServiceHandlers(request);
    }
}





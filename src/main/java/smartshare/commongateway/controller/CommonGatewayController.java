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

//    @GetMapping(value="**")
//    public ResponseEntity<Map<String, String>> testMethod(RequestEntity request){
//        System.out.println("inside");
//        System.out.println(request);
//
////        Have to sort out on HTTP status issue for correct and error result
//
//        return new ResponseEntity<>( service.processRequests(request),HttpStatus.OK);
//    }

    @RequestMapping(
            value = "**",
            method = {RequestMethod.GET, RequestMethod.POST}
    )
    public ResponseEntity forwardPostRequestsToServiceHandlers(RequestEntity request) {
        log.info("In forwardPostRequestsToRespectiveServiceHandlers");
        System.out.println(request);
        return service.forwardPostRequestsToRespectiveServiceHandlers(request);
    }
}





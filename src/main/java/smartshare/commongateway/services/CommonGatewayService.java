package smartshare.commongateway.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class CommonGatewayService {

    private RequestHandlerFactory requestHandlerFactory;

    @Autowired
    CommonGatewayService(RequestHandlerFactory requestHandlerFactory) {
        this.requestHandlerFactory = requestHandlerFactory;
    }
    public ResponseEntity forwardPostRequestsToRespectiveServiceHandlers(RequestEntity request) {
        log.info("Inside forwardPostRequestsToRespectiveServiceHandlers");
        return requestHandlerFactory.getRequestHandlerForIncomingRequest( request.getUrl().getPath() ).processRequest( request );

    }
}


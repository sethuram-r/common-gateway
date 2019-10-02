package smartshare.commongateway.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import smartshare.commongateway.model.redis.UserSessionDetail;
import smartshare.newcommongateway.model.Status;

import java.util.Map;
import java.util.Objects;


@Slf4j
@Service
public class CommonGatewayService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    JWTService jwtService;

    @Autowired
    UserSessionDetailsService userSessionDetailsService;

    public ResponseEntity forwardPostRequestsToRespectiveServiceHandlers(RequestEntity request) {
        log.info("Inside forwardPostRequestsToRespectiveServiceHandlers");

        System.out.println(request.getUrl().getPath());
        String url = "";
        ResponseEntity<Object> responseEntity = null;

        try {

            switch (request.getUrl().getPath()) {

                case "/authenticate/signUp":
                    System.out.println("inside signup");
                    url = "http://localhost:8082/signUp";
                    responseEntity = restTemplate.postForEntity(url, request.getBody(), Object.class);
                    break;

                case "/authenticate/signIn":
                    System.out.println("inside signin");
                    url = "http://localhost:8082/signIn";
                    responseEntity = restTemplate.postForEntity(url, request.getBody(), Object.class);
                    Map temp = (Map) Objects.requireNonNull(responseEntity.getBody());
                    System.out.println("temp----------->" + temp);
                    UserSessionDetail userSessionDetailsToBePersisted = new UserSessionDetail(temp);
                    try {
                        UserSessionDetail sessionSaveResult = userSessionDetailsService.saveUserSessionDetail(userSessionDetailsToBePersisted);
                        System.out.println("sessionSaveResult---------------->" + sessionSaveResult);
                        HttpHeaders responseHeaders = new HttpHeaders();
                        responseHeaders.set("Access-Control-Expose-Headers", JWTService.TOKEN_NAME);
                        responseHeaders.set(JWTService.TOKEN_NAME, jwtService.generateToken((Map) Objects.requireNonNull(responseEntity.getBody())));
                        Map responseToBeSentBackToClient = (Map) responseEntity.getBody();
                        responseToBeSentBackToClient.keySet().removeIf(key -> key == "sessionId");
                        return ResponseEntity.ok().headers(responseHeaders).body(responseToBeSentBackToClient);
                    } catch (Exception exception) {
                        Status status = new Status(exception.getMessage().concat(String.valueOf(exception.getCause())));
                        responseEntity = new ResponseEntity<>(status, HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                case "/file-service/stub":
                    url = "http://localhost:8082/stub";
                    System.out.println("inside stub");
                    responseEntity = restTemplate.getForEntity(url, Object.class);
                    System.out.println("responseEntity stub" + responseEntity);
                    break;

                default:
                    url = "http://localhost:8082/default";

            }
        } catch (final HttpClientErrorException httpClientErrorException) {
            System.out.println("Exception Occurred.." + httpClientErrorException.getStatusCode());
            System.out.println("httpClientErrorException Occurred.." + httpClientErrorException.getResponseBodyAsString());
            Status status = new Status(httpClientErrorException.getResponseBodyAsString());
            responseEntity = new ResponseEntity<>(status, httpClientErrorException.getStatusCode());
        } catch (Exception exception) {
            System.out.println("Exception Occurred.." + exception.getMessage());

        }

        System.out.println("responseEntity");
        System.out.println(responseEntity);
        return responseEntity;
    }
}


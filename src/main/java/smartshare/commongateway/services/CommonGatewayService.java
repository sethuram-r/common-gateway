package smartshare.commongateway.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import smartshare.commongateway.model.Status;


@Service
public class CommonGatewayService {

    @Autowired
    RestTemplate restTemplate;

    public ResponseEntity processRequests(RequestEntity request) {

        System.out.println("inside service");
        System.out.println(request.getUrl().getPath());
        String url = "";
        ResponseEntity<Object> responseEntity = null;

        try {

            switch (request.getUrl().getPath()) {

                case "/signUp":
                    System.out.println("inside signup");
                    url = "http://localhost:8082/signUp";
                    responseEntity = restTemplate.postForEntity(url, request.getBody(), Object.class);
                    break;

                case "/signIn":
                    System.out.println("inside signin");
                    url = "http://localhost:8082/signIn";
                    responseEntity = restTemplate.getForEntity(url, Object.class);
                    break;

                default:
                    url = "http://localhost:8082/default";

            }
        } catch (final HttpClientErrorException httpClientErrorException) {
            System.out.println("httpClientErrorException Occurred.." + httpClientErrorException.getResponseBodyAsString());
            Status status = new Status(httpClientErrorException.getResponseBodyAsString());
            responseEntity = new ResponseEntity<>(status, HttpStatus.BAD_REQUEST);
        } catch (Exception exception) {
            System.out.println("Exception Occurred.." + exception.getMessage());

        }

        System.out.println("responseEntity");
        System.out.println(responseEntity);
        return responseEntity;
    }
}

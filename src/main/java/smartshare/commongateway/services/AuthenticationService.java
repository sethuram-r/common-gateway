package smartshare.commongateway.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class AuthenticationService {

    @Autowired
    RestTemplate restTemplate;

    public Map<String, String> processRequests(RequestEntity request){

        System.out.println("inside service");
        System.out.println(request.getUrl().getPath());
        String url = "";
        ResponseEntity<Object> responseEntity = null;

        switch ( request.getUrl().getPath()){

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

        System.out.println("responseEntity");
        return  (Map<String, String>) responseEntity.getBody();
    }
}

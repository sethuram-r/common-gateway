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
        String url = "";

        switch ( request.getUrl().getPath()){

            case "/signIn":
                url = "http://localhost:8082/signIn";

        }
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(url,Object.class);
        System.out.println("responseEntity");
        System.out.println(responseEntity);
        return  (Map<String, String>) responseEntity.getBody();
    }
}

package smartshare.commongateway.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class RequestHandlerFactory {


    private SignUpRequestHandler signUpRequestHandler;
    private SignInRequestHandler signInRequestHandler;
    private StubHandler stubHandler;

    @Autowired
    public RequestHandlerFactory(SignUpRequestHandler signUpRequestHandler, SignInRequestHandler signInRequestHandler, StubHandler stubHandler) {
        this.signUpRequestHandler = signUpRequestHandler;
        this.signInRequestHandler = signInRequestHandler;
        this.stubHandler = stubHandler;
    }

    public RequestHandler getRequestHandlerForIncomingRequest(String requestPathToBeProcessed) {

        switch (requestPathToBeProcessed) {
            case "/authenticate/signUp":
                return signUpRequestHandler;
            case "/authenticate/signIn":
                return signInRequestHandler;
            case "/file-service/stub":
                return stubHandler;
            default:
                System.out.println( "I am Done Forwarding......" );
                return null;
        }
    }
}

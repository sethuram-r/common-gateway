package smartshare.commongateway.exception;

public class TokenNotFoundException extends RuntimeException {
    private String message;

    public TokenNotFoundException() {
        message = " Required Token";
    }

    @Override
    public String getMessage() {
        return message;
    }
}

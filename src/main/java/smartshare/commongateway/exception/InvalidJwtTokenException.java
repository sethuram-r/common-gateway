package smartshare.commongateway.exception;


public class InvalidJwtTokenException extends RuntimeException {
    private String message = "Invalid Token";

    @Override
    public String getMessage() {
        return message;
    }
}




package smartshare.commongateway.exception;

public class ExpiredJwtTokenException extends RuntimeException {

    private String message = "Token has been Expired";

    @Override
    public String getMessage() {
        return message;
    }

}



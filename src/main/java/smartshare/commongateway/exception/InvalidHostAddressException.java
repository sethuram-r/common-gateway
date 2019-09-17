package smartshare.commongateway.exception;

public class InvalidHostAddressException extends RuntimeException {

    private String message;

    public InvalidHostAddressException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

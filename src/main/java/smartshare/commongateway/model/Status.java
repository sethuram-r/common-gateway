package smartshare.newcommongateway.model;

public class Status {

    private String message;

    public Status(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }


    @Override
    public String toString() {
        return "Status{" +
                "message='" + message + '\'' +
                '}';
    }
}

package smartshare.commongateway.constant;

public enum ResourcePathToBeExcluded {

    AUTHENTICATE("/authenticate");

    private final String urlsActualRepresentation;

    ResourcePathToBeExcluded(String urlsActualRepresentation) {
        this.urlsActualRepresentation = urlsActualRepresentation;
    }

    public String getUrlsActualRepresentation() {
        return urlsActualRepresentation;
    }

}




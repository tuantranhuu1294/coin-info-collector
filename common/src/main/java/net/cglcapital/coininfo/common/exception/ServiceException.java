package net.cglcapital.coininfo.common.exception;

public class ServiceException extends RuntimeException {

    private final Error error;

    public ServiceException(Error error) {
        super(error.getMessage());
        this.error = error;
    }

    public ServiceException(Error error, Throwable throwable) {
        super(throwable);
        this.error = error;
    }

    public ServiceException(Error error, String message) {
        super(message);
        this.error = new Error(error);
        this.error.setMessage(message);
    }

    public Error getError() {
        return error;
    }
}

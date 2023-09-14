package be.bds.bdsbes.exception;

import be.bds.bdsbes.utils.dto.ValidationErrorResponse;

import java.util.List;

public class ServiceException extends Throwable {

    private List<ValidationErrorResponse> errors;

    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, List<ValidationErrorResponse> errors) {
        super(message);
        this.errors = errors;
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(String message, List<ValidationErrorResponse> errors, Throwable cause) {
        super(message, cause);
        this.errors = errors;
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public ServiceException(List<ValidationErrorResponse> errors, Throwable cause) {
        super(cause);
        this.errors = errors;
    }

    public List<ValidationErrorResponse> getErrors() {
        return errors;
    }

    public void setErrors(List<ValidationErrorResponse> errors) {
        this.errors = errors;
    }

    protected ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

package be.bds.bdsbes.utils;

import be.bds.bdsbes.exception.ServiceException;
import be.bds.bdsbes.utils.dto.ValidationErrorResponse;

import java.util.ArrayList;
import java.util.List;

public class ServiceExceptionBuilderUtil {
    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {

        public List<ValidationErrorResponse> errors;

        public Builder() {
            errors = new ArrayList<>();
        }

        public Builder addError(ValidationErrorResponse errorResponse) {
            errors.add(errorResponse);
            return this;
        }

        public ServiceException build() {
            return new ServiceException(ServiceResponseMessageUtil.Failed, errors);
        }
    }

}
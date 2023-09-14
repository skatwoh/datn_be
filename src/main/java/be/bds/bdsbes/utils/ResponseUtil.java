package be.bds.bdsbes.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import be.bds.bdsbes.exception.ServiceException;
import be.bds.bdsbes.utils.dto.BaseDataResponse;
import be.bds.bdsbes.utils.dto.KeyValue;
import be.bds.bdsbes.utils.dto.ValidationErrorResponse;


import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface ResponseUtil {

    static <X> ResponseEntity<X> wrapOrNotFound(Optional<X> maybeResponse) {
        return wrapOrNotFound(maybeResponse, (HttpHeaders) null);
    }

    static <X> ResponseEntity<X> wrapOrNotFound(Optional<X> maybeResponse, HttpHeaders header) {
        return (ResponseEntity) maybeResponse.map((response) -> {
            return ((ResponseEntity.BodyBuilder) ResponseEntity.ok().headers(header)).body(response);
        }).orElse(new ResponseEntity(HttpStatus.NOT_FOUND));
    }

    static ResponseEntity generateErrorResponse() {
        return generateErrorResponse(ServiceResponseMessageUtil.Failed);
    }

    static ResponseEntity generateErrorResponse(String message) {
        BaseDataResponse response = new BaseDataResponse();
        response.setResponseCode(ServiceResponseCodeUtil.Failed);
        response.setResponseMessage(message);

        return new ResponseEntity(response, HttpStatus.OK);
    }

    static ResponseEntity generateErrorResponse(ServiceException exception) {
        BaseDataResponse response = new BaseDataResponse();
        response.setResponseCode(ServiceResponseCodeUtil.Failed);
        response.setResponseMessage(exception.getMessage());
        response.setResponseEntityMessages(exception.getErrors());

        return new ResponseEntity(response, HttpStatus.OK);
    }

    static ResponseEntity generateErrorResponse(Exception exception) {
        Throwable t = exception.getCause();

        if (exception instanceof ConstraintViolationException)
            t = exception;
        else {
            while ((t != null) && !(t instanceof ConstraintViolationException)) {
                t = t.getCause();
            }
        }

        if (t instanceof ConstraintViolationException) {
            return generateErrorResponse((ConstraintViolationException) t);
        } else if (t instanceof ServiceException) {
            return generateErrorResponse((ServiceException) t);
        } else {
            return generateErrorResponse();
        }
    }

    static ResponseEntity generateErrorResponse(ConstraintViolationException exception) {
        return generateErrorResponse(exception, null);
    }

    static ResponseEntity generateErrorResponse(ConstraintViolationException exception, String messageKeyPrefix) {
        BaseDataResponse response = new BaseDataResponse();
        response.setResponseCode(ServiceResponseCodeUtil.Failed);
        response.setResponseMessage(ServiceResponseMessageUtil.Failed);

        List<ValidationErrorResponse> errors = new ArrayList<>();
        List<KeyValue> errorMessageParams;

        for (ConstraintViolation cv : exception.getConstraintViolations()) {

            errorMessageParams = null;
            String errorMessage = MessageUtil.getMessage(cv.getMessageTemplate());

            if (cv.getConstraintDescriptor() != null
                    && cv.getConstraintDescriptor().getAttributes() != null
                    && !StringUtils.isEmpty(errorMessage)) {
                Map<String, Object> constraints = cv.getConstraintDescriptor().getAttributes();

                Pattern pattern = Pattern.compile("\\{(.*?)\\}");
                Matcher match = pattern.matcher(errorMessage);
                String messageParam;

                while (match.find()) {

                    if (errorMessageParams == null)
                        errorMessageParams = new ArrayList<>();

                    messageParam = match.group()
                            .replace("{", "")
                            .replace("}", "");

                    String constraintValue = constraints.get(messageParam) != null
                            ? constraints.get(messageParam).toString() : null;

                    if (StringUtils.isEmpty(constraintValue))
                        continue;

                    errorMessageParams.add(new KeyValue(messageParam, constraintValue));
                }
            }

            errors.add(new ValidationErrorResponse(cv.getPropertyPath().toString(),
                    cv.getMessageTemplate(),
                    errorMessage,
                    errorMessageParams));
        }

        response.setResponseEntityMessages(errors);

        return new ResponseEntity(response, HttpStatus.OK);
    }

    static ResponseEntity wrap(Object object) {
        return wrap(null, object);
    }

    static ResponseEntity wrap(HttpHeaders headers, Object object) {
        BaseDataResponse response = new BaseDataResponse();
        response.setResponseCode(ServiceResponseCodeUtil.Successful);
        response.setResponseMessage(ServiceResponseMessageUtil.Successful);
        response.setBody(object);

        return new ResponseEntity(response, headers, HttpStatus.OK);
    }

    static ResponseEntity unwrap(HttpHeaders headers, Object object) {
        return new ResponseEntity(object, headers, HttpStatus.OK);
    }
}
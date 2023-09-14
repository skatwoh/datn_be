package be.bds.bdsbes.utils.dto;

import be.bds.bdsbes.utils.MessageUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidationErrorResponse {
    private String key;
    private String errorCode;
    private String errorMessage;
    private List<KeyValue> params;

    public ValidationErrorResponse(String key, String errorCode) {
        this.key = key;
        this.errorCode = errorCode;
        this.errorMessage = MessageUtil.getMessage(errorCode);
    }

    public ValidationErrorResponse(String key, String errorCode, String errorMessage) {
        this.key = key;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public ValidationErrorResponse(String key, String errorCode, List<KeyValue> params) {
        this.key = key;
        this.errorCode = errorCode;
        this.errorMessage = MessageUtil.getMessage(errorCode);
        this.params= params;
    }
}
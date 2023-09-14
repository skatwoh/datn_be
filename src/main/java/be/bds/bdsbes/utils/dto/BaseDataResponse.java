package be.bds.bdsbes.utils.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseDataResponse<T> {

    @JsonProperty("code")
    private String responseCode;

    @JsonProperty("message")
    private String responseMessage;

    @JsonProperty("entityMessages")
    private List<ValidationErrorResponse> responseEntityMessages;

    @JsonProperty("body")
    private T body;
}
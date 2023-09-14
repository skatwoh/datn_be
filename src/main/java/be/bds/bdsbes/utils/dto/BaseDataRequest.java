package be.bds.bdsbes.utils.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseDataRequest<T> {

    @Valid
    @JsonProperty("body")
    private T body;
}

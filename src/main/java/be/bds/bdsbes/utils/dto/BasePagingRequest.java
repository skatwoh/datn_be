package be.bds.bdsbes.utils.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasePagingRequest {
    private int pageNumber;
    private int pageSize;
}
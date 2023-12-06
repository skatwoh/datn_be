package be.bds.bdsbes.utils.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO for {@link be.bds.bdsbes.entities.Sale}
 */
@Getter
@Setter
public class SaleDto implements Serializable {
    Long id;
    String ma;
    String ten;
    BigDecimal giaTri;
    LocalDateTime ngayBatDau;
    LocalDateTime ngayKetThuc;
    Integer trangThai;
}
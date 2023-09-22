package be.bds.bdsbes.service.dto;

import lombok.Value;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO for {@link be.bds.bdsbes.entities.BaoTri}
 */
@Value
public class BaoTriDto implements Serializable {
    @NotNull
    LocalDate ngayBatDau;
    @NotNull
    LocalDate ngayKetThuc;
    @NotNull
    BigDecimal chiPhiBaoTri;
    String ghiChu;
    @NotNull
    Integer trangThai;
}
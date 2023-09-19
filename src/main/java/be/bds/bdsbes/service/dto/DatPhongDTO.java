package be.bds.bdsbes.service.dto;

import lombok.Value;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

/**
 * DTO for {@link be.bds.bdsbes.entities.DatPhong}
 */
@Value
public class DatPhongDTO implements Serializable {
    UUID id;
    @NotNull
    LocalDate ngayDat;
    @NotNull
    LocalDate checkIn;
    @NotNull
    LocalDate checkOut;
    @NotNull
    Integer soNguoi;
    String ghiChu;
    Integer trangThai;
}
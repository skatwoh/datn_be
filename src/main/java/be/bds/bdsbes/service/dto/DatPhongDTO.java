package be.bds.bdsbes.service.dto;

import lombok.Value;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

/**
 * DTO for {@link be.bds.bdsbes.entities.DatPhong}
 */
@Value
public class DatPhongDTO implements Serializable {

    LocalDate ngayDat;

    @NotNull(message = "Bạn cần chọn ngày check-in")
    LocalDate checkIn;

    @NotNull(message = "Bạn cần chọn ngày check-out")
    LocalDate checkOut;

    @NotNull(message = "Vui lòng điền số người")
    Integer soNguoi;

    Long userId;

    Long idPhong;

    Long idVoucher;

    String ghiChu;

    Integer trangThai;
}
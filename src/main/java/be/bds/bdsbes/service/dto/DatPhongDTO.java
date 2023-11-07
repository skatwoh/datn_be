package be.bds.bdsbes.service.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link be.bds.bdsbes.entities.DatPhong}
 */
@Getter
@Setter
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
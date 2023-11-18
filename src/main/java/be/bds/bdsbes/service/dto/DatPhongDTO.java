package be.bds.bdsbes.service.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO for {@link be.bds.bdsbes.entities.DatPhong}
 */
@Getter
@Setter
public class DatPhongDTO implements Serializable {

    String ma;

    LocalDateTime ngayDat;

    @NotNull(message = "Bạn cần chọn ngày check-in")
    LocalDateTime checkIn;

    @NotNull(message = "Bạn cần chọn ngày check-out")
    LocalDateTime checkOut;

    @NotNull(message = "Vui lòng điền số người")
    Integer soNguoi;

    Long userId;

    Long idPhong;

    Long idVoucher;

    String ghiChu;

    Integer trangThai;

    BigDecimal tongGia;

}
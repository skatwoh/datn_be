package be.bds.bdsbes.service.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DatPhongResponse {

    private Long id;

    private Long idKhachHang;

    private String maKhachHang;

    private String soDienThoai;

    private Long idVoucher;

    private String maVoucher;

    private BigDecimal giamGia;

    private LocalDate ngayDat;

    private LocalDate checkIn;

    private LocalDate checkOut;

    private Integer soNguoi;

    private String ghiChu;

    private Integer trangThai;
}

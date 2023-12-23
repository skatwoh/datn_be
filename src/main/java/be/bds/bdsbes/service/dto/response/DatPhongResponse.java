package be.bds.bdsbes.service.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DatPhongResponse {

    private Long id;

    private String ma;

    private Long idKhachHang;

    private String maKhachHang;

    private String hoTen;

    private String sdt;

    private LocalDateTime ngayDat;

    private LocalDateTime checkIn;

    private LocalDateTime checkOut;

    private Integer soNguoi;

    private String ghiChu;

    private Integer trangThai;

    private String tenPhong;

    private BigDecimal tongGia;

    private Long idPhong;

    private BigDecimal giaPhong;

    private Long idHoaDon;
}

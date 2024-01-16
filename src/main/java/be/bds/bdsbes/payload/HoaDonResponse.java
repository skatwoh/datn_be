package be.bds.bdsbes.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HoaDonResponse {

    private Long id;

    private String ma;

    private LocalDateTime ngayTao;

    private LocalDateTime ngayThanhToan;

    private BigDecimal tongTien;

    private Integer trangThai;

    private String ghiChu;

    private Long idKhachHang;

    private String tenKhachHang;

}

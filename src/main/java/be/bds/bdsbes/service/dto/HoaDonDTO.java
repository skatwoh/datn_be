package be.bds.bdsbes.service.dto;

import be.bds.bdsbes.entities.DatPhong;
import be.bds.bdsbes.entities.HoaDon;
import be.bds.bdsbes.entities.KhachHang;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class HoaDonDTO {

    private Long id;

    private LocalDateTime ngayTao;

    private LocalDateTime ngayThanhToan;

    private BigDecimal tongTien;

    private Integer trangThai;

    private String ghiChu;

    private Long idKhachHang;

    public HoaDon dto(HoaDon hoaDon){
        LocalDateTime localDateTime = LocalDateTime.now();
        hoaDon.setNgayTao(localDateTime);
        hoaDon.setNgayThanhToan(this.getNgayThanhToan());
        hoaDon.setTongTien(this.getTongTien());
        hoaDon.setTrangThai(this.getTrangThai());
        hoaDon.setGhiChu(this.getGhiChu());
        hoaDon.setKhachHang(KhachHang.builder().id(this.getIdKhachHang()).build());
        return hoaDon;
    }
}

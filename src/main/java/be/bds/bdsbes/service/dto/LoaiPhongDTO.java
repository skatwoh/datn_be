package be.bds.bdsbes.service.dto;


import be.bds.bdsbes.entities.LoaiPhong;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Getter
@Setter
public class LoaiPhongDTO {

    private String maLoaiPhong;

    @NotBlank(message = "Không được để trống")
    private String tenLoaiPhong;

    private int soNguoi;

    private String tienIch;

    private String ghiChu;

    private BigDecimal giaTheoNgay;

    private BigDecimal giaTheoGio;


    public LoaiPhong dto(LoaiPhong loaiPhong){
        loaiPhong.setTenLoaiPhong(this.getTenLoaiPhong());
        loaiPhong.setSoNguoi(this.getSoNguoi());
        loaiPhong.setTienIch(this.getTienIch());
        loaiPhong.setGhiChu(this.getGhiChu());
        loaiPhong.setGiaTheoNgay(this.getGiaTheoNgay());
        loaiPhong.setGiaTheoGio(this.getGiaTheoGio());
        return loaiPhong;
    }
}

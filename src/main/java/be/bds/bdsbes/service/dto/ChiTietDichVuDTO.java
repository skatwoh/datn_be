package be.bds.bdsbes.service.dto;

import be.bds.bdsbes.entities.ChiTietDichVu;
import be.bds.bdsbes.entities.DatPhong;
import be.bds.bdsbes.entities.DichVu;
import be.bds.bdsbes.entities.TheThanhVien;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ChiTietDichVuDTO {
    private  Long idDichVu;
    private Long idDatPhong;
    private String ghiChu;
    private BigDecimal giaDichVu;
    private  Integer trangThai = 1;

    public ChiTietDichVu dto(ChiTietDichVu chiTietDichVu){
        chiTietDichVu.setIdDichVu(DichVu.builder().id(this.getIdDichVu()).build());
        chiTietDichVu.setIdDatPhong(DatPhong.builder().id(this.getIdDatPhong()).build());
        chiTietDichVu.setGhiChu(this.getGhiChu());
        chiTietDichVu.setGiaDichVu(this.getGiaDichVu());
        chiTietDichVu.setTrangThai(this.getTrangThai());
        return chiTietDichVu;
    }
}

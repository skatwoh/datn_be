package be.bds.bdsbes.service.dto;

import be.bds.bdsbes.entities.BaoTri;
import be.bds.bdsbes.entities.ChiTietPhong;
import be.bds.bdsbes.entities.QuanLyDoiTac;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO for {@link be.bds.bdsbes.entities.BaoTri}
 */

@Getter
@Setter
public class BaoTriDto {

    @NotNull
    LocalDate ngayBatDau;
    @NotNull
    LocalDate ngayKetThuc;
    @NotNull
    BigDecimal chiPhiBaoTri;
    String ghiChu;
    @NotNull
    Integer trangThai;

    @NotNull
    Long idChiTietPhong;

    public BaoTri dto(BaoTri baoTri) {
        baoTri.setNgayBatDau(this.getNgayBatDau());
        baoTri.setNgayKetThuc(this.getNgayKetThuc());
        baoTri.setChiPhiBaoTri(this.getChiPhiBaoTri());
        baoTri.setGhiChu(this.getGhiChu());
        baoTri.setTrangThai(this.getTrangThai());
        baoTri.setChiTietPhong(ChiTietPhong.builder().id(this.getIdChiTietPhong()).build());
        return baoTri;
    }
}
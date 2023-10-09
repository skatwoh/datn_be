package be.bds.bdsbes.service.dto;

import be.bds.bdsbes.entities.DuAn;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDate;
@Getter
@Setter
public class DuAnDTO {
    private String ma;
    private String ten;

    private Double tienDo ;

    private BigDecimal chiPhi;


    private LocalDate ngayBatDau;

    private LocalDate ngayKetThuc;

    private String ghiChu;

    private Integer trangThai = 1;


    public DuAn dto(DuAn duAn){
        duAn.setMa(this.getMa());
        duAn.setTen(this.getTen());
        duAn.setTienDo(this.getTienDo());
        duAn.setChiPhi(this.getChiPhi());
        duAn.setNgayBatDau(this.getNgayBatDau());
        duAn.setNgayKetThuc(this.getNgayKetThuc());
        duAn.setGhiChu(this.getGhiChu());
        duAn.setTrangThai(this.getTrangThai());
        return duAn;
    }
}

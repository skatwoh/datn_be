package be.bds.bdsbes.service.dto;

import be.bds.bdsbes.entities.QuanLyDoiTac;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class QuanLyDoiTacDTO {

    private String ma;

    @NotBlank(message = "Khong duoc de trong")
    @Length(max = 50, message = "Khong duoc vuot qua 50 ki tu")
    private String tenCongTy;

    @NotBlank(message = "Khong duoc de trong")
    @Length(max = 50, message = "Khong duoc vuot qua 50 ki tu")
    private String ghiChu;

    private Integer trangThai;


    public QuanLyDoiTac dto(QuanLyDoiTac quanLyDoiTac) {

        quanLyDoiTac.setTenCongTy(this.getTenCongTy());
        quanLyDoiTac.setGhiChu(this.getGhiChu());
        quanLyDoiTac.setTrangThai(this.getTrangThai());
        return quanLyDoiTac;
    }
}

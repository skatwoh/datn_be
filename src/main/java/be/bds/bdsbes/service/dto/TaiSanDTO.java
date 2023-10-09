package be.bds.bdsbes.service.dto;

import be.bds.bdsbes.entities.TaiSan;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
@Getter
@Setter
public class TaiSanDTO {
    @NotNull(message = "Not allow null")
    @NotBlank(message = "Not blank")
    @Length(max = 20, message = "Length must not exceed 20 characters")
    private String ma;

    @NotNull(message = "Not allow null")
    @NotBlank(message = "Not blank")
    @Length(max = 20, message = "Length must not exceed 20 characters")
    private String ten;


    @NotNull(message = "Not allow null")
    @NotBlank(message = "Not blank")
    @Length(max = 100, message = "Length must not exceed 100 characters")
    private String ghiChu;

    private Integer trangThai = 1;

    public TaiSan dto(TaiSan taiSan) {
        taiSan.setMa(this.getMa());
        taiSan.setTen(this.getTen());
        taiSan.setGhiChu(this.getGhiChu());
        taiSan.setTrangThai(this.getTrangThai());

        return taiSan;
    }
}

package be.bds.bdsbes.service.dto;

import be.bds.bdsbes.entities.DichVu;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
@Getter
@Setter
public class DichVuDTO {
    @NotNull(message = "Not allow null")
    @NotBlank(message = "Not blank")
    @Length(max = 20, message = "Length must not exceed 20 characters")
    private String ma;
    @NotNull(message = "Not allow null")
    @NotBlank(message = "Not blank")
    @Length(max = 20, message = "Length must not exceed 20 characters")
    private String tenDichVu;
    @NotNull(message = "Not allow null")
    @NotBlank(message = "Not blank")
    @Length(max = 20, message = "Length must not exceed 20 characters")
    private String ghiChu;
    @NotNull(message = "Not allow null")
    private BigDecimal giaDichVu;
    private  Integer trangThai = 1;

    public DichVu dto(DichVu dichVu){
        dichVu.setMa(this.getMa());
        dichVu.setTenDichVu(this.getTenDichVu());
        dichVu.setGhiChu(this.getGhiChu());
        dichVu.setGiaDichVu(this.getGiaDichVu());
        dichVu.setTrangThai(this.getTrangThai());
        return dichVu;
    }
}

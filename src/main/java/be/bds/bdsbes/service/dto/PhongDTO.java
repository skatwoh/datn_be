package be.bds.bdsbes.service.dto;

import be.bds.bdsbes.entities.LoaiPhong;
import be.bds.bdsbes.entities.Phong;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
public class PhongDTO {

    private String ma;

    @NotNull(message = "Not allow null")
    private Long idLoaiPhong;

    @NotNull(message = "Not allow null")
    private BigDecimal giaPhong;

    @NotNull(message = "Not allow null")
    private Integer trangThai;

    public Phong dto(Phong phong){
        phong.setGiaPhong(this.getGiaPhong());
        phong.setTrangThai(this.getTrangThai());
        phong.setLoaiPhong(LoaiPhong.builder().id(this.getIdLoaiPhong()).build());
        return phong;
    }
}

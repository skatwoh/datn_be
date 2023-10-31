package be.bds.bdsbes.service.dto;


import be.bds.bdsbes.entities.ChiTietDatPhong;
import be.bds.bdsbes.entities.ChiTietPhong;
import be.bds.bdsbes.entities.DatPhong;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ChiTietDatPhongDTO {

    @NotNull(message = "Not allow null")
    private Long idDatPhong;

    @NotNull(message = "Not allow null")
    private Long idChiTietPhong;

    private String ghiChu;

    public ChiTietDatPhong dto(ChiTietDatPhong chiTietDatPhong){
        chiTietDatPhong.setChiTietPhong(ChiTietPhong.builder().id(this.getIdChiTietPhong()).build());
        chiTietDatPhong.setDatPhong(DatPhong.builder().id(this.getIdDatPhong()).build());
        chiTietDatPhong.setGhiChu(this.getGhiChu());
        return chiTietDatPhong;
    }
}

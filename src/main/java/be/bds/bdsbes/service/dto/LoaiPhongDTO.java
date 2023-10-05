package be.bds.bdsbes.service.dto;


import be.bds.bdsbes.entities.LoaiPhong;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class LoaiPhongDTO {

    @NotBlank(message = "Không được để trống")
    private String maLoaiPhong;

    @NotBlank(message = "Không được để trống")
    private String tenLoaiPhong;

    private String ghiChu;

    public LoaiPhong dto(LoaiPhong loaiPhong){
        loaiPhong.setMaLoaiPhong(this.getMaLoaiPhong());
        loaiPhong.setTenLoaiPhong(this.getTenLoaiPhong());
        loaiPhong.setGhiChu(this.getGhiChu());
        return loaiPhong;
    }
}

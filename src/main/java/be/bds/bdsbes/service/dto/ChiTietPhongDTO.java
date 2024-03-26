package be.bds.bdsbes.service.dto;

import be.bds.bdsbes.entities.ChiTietPhong;
import be.bds.bdsbes.entities.LoaiPhong;
import be.bds.bdsbes.entities.Phong;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ChiTietPhongDTO {

    private String tang;

    @NotNull(message = "Not allow null")
    @NotBlank(message = "Không được để trống")
    private String dichVu;

    @NotNull(message = "Not allow null")
    private Double dienTich;

    @NotNull(message = "Not allow null")
    private Integer trangThai;

    private Long idPhong;

    public ChiTietPhong dto(ChiTietPhong chiTietPhong){
        chiTietPhong.setDichVu(this.getDichVu());
        chiTietPhong.setDienTich(this.getDienTich());
        chiTietPhong.setTrangThai(this.getTrangThai());
        chiTietPhong.setPhong(Phong.builder().id(this.getIdPhong()).build());
        return chiTietPhong;
    }
}

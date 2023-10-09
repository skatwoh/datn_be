package be.bds.bdsbes.service.dto;

import be.bds.bdsbes.entities.ChiTietPhong;
import be.bds.bdsbes.entities.LoaiPhong;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ChiTietPhongDTO {

    @NotNull(message = "Not allow null")
    @NotBlank(message = "Không được để trống")
    private String tang;

    @NotNull(message = "Not allow null")
    @NotBlank(message = "Không được để trống")
    private String tienIch;

    @NotNull(message = "Not allow null")
    @NotBlank(message = "Không được để trống")
    private String dichVu;

    @NotNull(message = "Not allow null")
    private Integer soLuongNguoi;

    @NotNull(message = "Not allow null")
    private Double dienTich;

    @NotNull(message = "Not allow null")
    private Integer trangThai;

    private Long idLoaiPhong;

    public ChiTietPhong dto(ChiTietPhong chiTietPhong){
        chiTietPhong.setTang(this.getTang());
        chiTietPhong.setDichVu(this.getDichVu());
        chiTietPhong.setTienIch(this.getTienIch());
        chiTietPhong.setSoLuongNguoi(this.getSoLuongNguoi());
        chiTietPhong.setDienTich(this.getDienTich());
        chiTietPhong.setTrangThai(this.getTrangThai());
        chiTietPhong.setIdLoaiPhong(LoaiPhong.builder().id(this.getIdLoaiPhong()).build());
        return chiTietPhong;
    }
}
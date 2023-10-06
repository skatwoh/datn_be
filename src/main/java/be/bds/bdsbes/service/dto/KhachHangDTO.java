package be.bds.bdsbes.service.dto;

import be.bds.bdsbes.entities.KhachHang;
import be.bds.bdsbes.entities.TaiKhoan;
import be.bds.bdsbes.entities.TheThanhVien;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class KhachHangDTO {

    private String ma;
    private LocalDate ngaySinh;
    private Boolean gioiTinh;
    private String diaChi;
    private String sdt;

    private Long idTheThanhVien;

    public KhachHang dto(KhachHang khachHang){
        khachHang.setMa(this.getMa());
        khachHang.setSdt(this.getSdt());
        khachHang.setDiaChi(this.getDiaChi());
        khachHang.setNgaySinh(this.getNgaySinh());
        khachHang.setIdTheThanhVien(TheThanhVien.builder().id(this.getIdTheThanhVien()).build());
        return khachHang;
    }
}

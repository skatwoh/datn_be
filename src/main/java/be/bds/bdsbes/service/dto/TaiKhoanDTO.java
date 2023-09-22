package be.bds.bdsbes.service.dto;

import be.bds.bdsbes.entities.KhachHang;
import be.bds.bdsbes.entities.TaiKhoan;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

@Getter
@Setter
public class TaiKhoanDTO {

    Random random = new Random();
    int min = 1;
    int max = Integer.MAX_VALUE;
    int ma = random.nextInt(max - min +1) + min;
    @NotBlank(message = "Not blank")
    private String email;

    @NotBlank(message = "Not blank")
    private String matKhau;

    @NotBlank(message = "Not blank")
    private String ten;

    private Long idKhachHang;

    @NotNull(message = "Not blank")
    private Integer trangThai;

    public TaiKhoan dto(TaiKhoan taiKhoan) {
        taiKhoan.setEmail(this.getEmail());
        taiKhoan.setMatKhau(this.getMatKhau());
        taiKhoan.setTrangThai(this.getTrangThai());
        taiKhoan.setTen(this.getTen());
        taiKhoan.setIdKhachHang(KhachHang.builder().id(this.getIdKhachHang()).ma("KH" + ma).build());
        return taiKhoan;
    }
}

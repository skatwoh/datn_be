package be.bds.bdsbes.service.dto;

import be.bds.bdsbes.entities.KhachHang;
import be.bds.bdsbes.entities.TaiKhoan;
import be.bds.bdsbes.entities.TheThanhVien;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

@Getter
@Setter
public class TaiKhoanDTO {

    Random random = new Random();
    int min = 1;
    int max = Integer.MAX_VALUE;
    int ma = random.nextInt(max - min + 1) + min;
    @NotBlank(message = "Not blank")
    private String email;

    @NotBlank(message = "Not blank")
    @Length(min = 8, message = "Mật khẩu tối thiếu 8 ký tự")
    @Length(max = 30, message = "Mật khẩu không được vượt quá 30 ký tự")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]){8,20}$")
    private String matKhau;

    @NotBlank(message = "Not blank")
    private String ten;

    private Long idKhachHang;

    private Integer trangThai = 1;

    public TaiKhoan dto(TaiKhoan taiKhoan) {
        taiKhoan.setEmail(this.getEmail());
        taiKhoan.setMatKhau(this.getMatKhau());
        taiKhoan.setTrangThai(this.getTrangThai());
        taiKhoan.setTen(this.getTen());
        taiKhoan.setKhachHang(KhachHang.builder().id(this.getIdKhachHang()).ma("KH" + ma).hoTen(this.getTen())
                .theThanhVien(TheThanhVien.builder().id(1L).build()).build());
        return taiKhoan;
    }
}

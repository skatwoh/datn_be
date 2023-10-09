package be.bds.bdsbes.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaiKhoanResponse1 {
    private Long id;
    private String email;
    private String matKhau;
    private Integer trangThai;
    private String ten;
    private String maKhachHang;
}

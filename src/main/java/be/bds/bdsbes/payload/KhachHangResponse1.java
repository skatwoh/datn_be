package be.bds.bdsbes.payload;

import be.bds.bdsbes.entities.TheThanhVien;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KhachHangResponse1 {

    private Long id;

    private String ma;

    private String hoTen;

    private LocalDate ngaySinh;

    private Boolean gioiTinh;

    private String diaChi;

    private String sdt;

    private String cccd;

}

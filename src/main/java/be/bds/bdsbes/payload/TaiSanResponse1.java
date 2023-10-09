package be.bds.bdsbes.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaiSanResponse1 {
    private Long id;
    private String ma;
    private String ten;
    private String ghiChu;
    private Integer trangThai;
}

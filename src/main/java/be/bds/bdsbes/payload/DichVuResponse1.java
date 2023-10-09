package be.bds.bdsbes.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DichVuResponse1 {
    private Long id;
    private String ma;
    private String tenDichVu;
    private String ghiChu;
    private BigDecimal giaDichVu;
    private Integer trangThai;

}

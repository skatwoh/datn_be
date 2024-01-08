package be.bds.bdsbes.payload;

import be.bds.bdsbes.entities.DatPhong;
import be.bds.bdsbes.entities.DichVu;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChiTietDichVuResponse1 {
    private Long id;
    private Long idDichVu;
    private String tenDichVu;
    private Long idDatPhong;
    private String ghiChu;
    private BigDecimal giaDichVu;
    private Integer trangThai;
}

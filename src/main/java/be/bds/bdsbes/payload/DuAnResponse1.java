package be.bds.bdsbes.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DuAnResponse1 {
    private Long id;
    private String ma;
    private String ten;
    private Double tienDo;
    private BigDecimal chiPhi;
    private LocalDate ngayBatDau;
    private LocalDate ngayKetThuc;
    private String ghiChu;
    private Integer trangThai;
}

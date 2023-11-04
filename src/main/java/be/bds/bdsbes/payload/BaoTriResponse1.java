package be.bds.bdsbes.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaoTriResponse1 {

    Long id;

    LocalDate ngayBatDau;

    LocalDate ngayKetThuc;

    BigDecimal chiPhiBaoTri;

    String ghiChu;

    Integer trangThai;

    Long idChiTietPhong;
}

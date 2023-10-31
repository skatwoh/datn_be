package be.bds.bdsbes.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VoucherResponse {
    private Long id;

    private String ma;

    private String moTa;

    private BigDecimal giamGia;

    private LocalDate ngayBatDau;

    private LocalDate ngayKetThuc;

    private Integer trangThai;

    private Integer soLuong;


}

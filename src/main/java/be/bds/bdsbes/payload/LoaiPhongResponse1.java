package be.bds.bdsbes.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoaiPhongResponse1 {

    private Long id;

    private String maLoaiPhong;

    private String tenLoaiPhong;

    private int soNguoi;

    private String tienIch;

    private String ghiChu;

    private BigDecimal giaTheoNgay;

    private BigDecimal giaTheoGio;

}

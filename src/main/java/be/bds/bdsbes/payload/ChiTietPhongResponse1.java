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
public class ChiTietPhongResponse1 {

    private Long id;

    private String tang;

    private String dichVu;

    private Double dienTich;

    private Integer trangThai;

    private Long idPhong;

    private String maPhong;

    private BigDecimal giaPhong;

    private String image;
}

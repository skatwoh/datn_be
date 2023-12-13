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
public class RoomMappingChiTietPhong {
    private Long id;

    private String ma;

    private BigDecimal giaPhong;

    private Integer trangThai;

    private String tenLoaiPhong;

    private String tang;

    private Integer soLuong;

}

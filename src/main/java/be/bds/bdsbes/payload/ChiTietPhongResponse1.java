package be.bds.bdsbes.payload;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChiTietPhongResponse1 {

    private Long id;

    private String tang;

    private String tienIch;

    private String dichVu;

    private Integer soLuongNguoi;

    private Double dienTich;

    private Integer trangThai;

    private Long idLoaiPhong;

    private String tenLoaiPhong;
}

package be.bds.bdsbes.entities;

import lombok.*;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = HoaDonChiTiet.TABLE_NAME)
public class HoaDonChiTiet {
    public static final String TABLE_NAME = "hoa_don_chi_tiet";
    public static final String COLUMN_ID_NAME = "id";
    public static final String COLUMN_GIA_NAME = "gia";
    public static final String COLUMN_GHICHU_NAME = "ghi_chu";
    public static final String COLUMN_TRANGTHAI_NAME = "trang_thai";


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = COLUMN_ID_NAME, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_hoa_don")
    private HoaDon hoaDon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_chi_tiet_phong")
    private ChiTietPhong chiTietPhong;

    @Column(name = COLUMN_GIA_NAME, precision = 18)
    private BigDecimal gia;

    @Size(max = 100)
    @Nationalized
    @Column(name = COLUMN_GHICHU_NAME, length = 100)
    private String ghiChu;

    @Column(name = COLUMN_TRANGTHAI_NAME)
    private Integer trangThai;

}
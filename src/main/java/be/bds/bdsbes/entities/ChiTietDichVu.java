package be.bds.bdsbes.entities;

import lombok.*;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = ChiTietDichVu.TABLE_NAME)
public class ChiTietDichVu {
    public static final String TABLE_NAME = "chi_tiet_dich_vu";
    public static final String COLUMN_ID_NAME = "id";
    public static final String COLUMN_GHICHU_NAME = "ghi_chu";
    public static final String COLUMN_GIADICHVU_NAME = "gia_dich_vu";
    public static final String COLUMN_TRANGTHAI_NAME = "trang_thai";


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = COLUMN_ID_NAME, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_dich_Vu")
    private DichVu idDichVu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_dat_phong")
    private DatPhong idDatPhong;

    @Nationalized
    @Lob
    @Column(name = COLUMN_GHICHU_NAME)
    private String ghiChu;

    @Column(name = COLUMN_GIADICHVU_NAME, precision = 18)
    private BigDecimal giaDichVu;

    @Column(name = COLUMN_TRANGTHAI_NAME)
    private Integer trangThai;

}
package be.bds.bdsbes.entities;

import lombok.*;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = BaoTri.TABLE_NAME)
public class BaoTri {
    public static final String TABLE_NAME = "bao_tri";
    public static final String COLUMN_ID_NAME = "id";
    public static final String COLUMN_NGAYBATDAU_NAME = "ngay_bat_dau";
    public static final String COLUMN_NGAYKETTHUC_NAME = "ngay_ket_thuc";
    public static final String COLUMN_CHIPHIBAOTRI_NAME = "chi_phi_bao_tri";
    public static final String COLUMN_GHICHU_NAME = "ghi_chu";
    public static final String COLUMN_TRANGTHAI_NAME = "trang_thai";


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = COLUMN_ID_NAME, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_chi_tiet_phong")
    private ChiTietPhong chiTietPhong;

    @Column(name = COLUMN_NGAYBATDAU_NAME)
    private LocalDate ngayBatDau;

    @Column(name = COLUMN_NGAYKETTHUC_NAME)
    private LocalDate ngayKetThuc;

    @Column(name = COLUMN_CHIPHIBAOTRI_NAME, precision = 18)
    private BigDecimal chiPhiBaoTri;

    @Nationalized
    @Lob
    @Column(name = COLUMN_GHICHU_NAME)
    private String ghiChu;

    @Column(name = COLUMN_TRANGTHAI_NAME)
    private Integer trangThai;

}
package be.bds.bdsbes.entities;

import lombok.*;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = DuAn.TABLE_NAME)
public class DuAn {
    public static final String TABLE_NAME = "du_an";
    public static final String COLUMN_ID_NAME = "id";
    public static final String COLUMN_MA_NAME = "ma";
    public static final String COLUMN_TEN_NAME = "ten";
    public static final String COLUMN_TIENDO_NAME = "tien_do";
    public static final String COLUMN_CHIPHI_NAME = "chi_phi";
    public static final String COLUMN_NGAYBATDAU_NAME = "ngay_bat_dau";
    public static final String COLUMN_NGAYKETTHUC_NAME = "ngay_ket_thuc";
    public static final String COLUMN_GHICHU_NAME = "ghi_chu";
    public static final String COLUMN_TRANGTHAI_NAME = "trang_thai";


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = COLUMN_ID_NAME, nullable = false)
    private Long id;

    @Size(max = 20)
    @Column(name = COLUMN_MA_NAME, length = 20)
    private String ma;

    @Size(max = 50)
    @Nationalized
    @Column(name = COLUMN_TEN_NAME, length = 50)
    private String ten;

    @Column(name = COLUMN_TIENDO_NAME)
    private Double tienDo;

    @Column(name = COLUMN_CHIPHI_NAME, precision = 18)
    private BigDecimal chiPhi;

    @Column(name = COLUMN_NGAYBATDAU_NAME)
    private LocalDate ngayBatDau;

    @Column(name = COLUMN_NGAYKETTHUC_NAME)
    private LocalDate ngayKetThuc;

    @Nationalized
    @Lob
    @Column(name = COLUMN_GHICHU_NAME)
    private String ghiChu;

    @Column(name = COLUMN_TRANGTHAI_NAME)
    private Integer trangThai;

}
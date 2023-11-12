package be.bds.bdsbes.entities;

import lombok.*;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = TheThanhVien.TABLE_NAME)
public class TheThanhVien {
    public static final String TABLE_NAME = "the_thanh_vien";
    public static final String COLUMN_ID_NAME = "id";
    public static final String COLUMN_MA_NAME = "ma";
    public static final String COLUMN_CAPBAC_NAME = "cap_bac";
    public static final String COLUMN_NGAYHETHAN_NAME = "ngay_het_han";
    public static final String COLUMN_GIAMGIA_NAME = "giam_gia";
    public static final String COLUMN_GHICHU_NAME = "ghi_chu";


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = COLUMN_ID_NAME, nullable = false)
    private Long id;

    @Size(max = 20)
    @Column(name = COLUMN_MA_NAME, length = 20)
    private String ma;

    @Size(max = 50)
    @Nationalized
    @Column(name = COLUMN_CAPBAC_NAME, length = 50)
    private String capBac;

    @Column(name = COLUMN_NGAYHETHAN_NAME)
    private LocalDate ngayHetHan;

    @Column(name = COLUMN_GIAMGIA_NAME, precision = 18)
    private BigDecimal giamGia;

    @Nationalized
    @Lob
    @Column(name = COLUMN_GHICHU_NAME)
    private String ghiChu;

    @OneToMany(mappedBy = "theThanhVien")
    private Set<KhachHang> khachHangs = new LinkedHashSet<>();

}
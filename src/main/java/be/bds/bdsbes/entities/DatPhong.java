package be.bds.bdsbes.entities;

import be.bds.bdsbes.domain.User;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = DatPhong.TABLE_NAME)
public class DatPhong {
    public static final String TABLE_NAME = "dat_phong";
    public static final String COLUMN_ID_NAME = "id";
    public static final String COLUMN_MA_NAME = "ma";
    public static final String COLUMN_NGAYDAT_NAME = "ngay_dat";
    public static final String COLUMN_CHECKIN_NAME = "check_in";
    public static final String COLUMN_CHECKOUT_NAME = "check_out";
    public static final String COLUMN_SONGUOI_NAME = "so_nguoi";
    public static final String COLUMN_GHICHU_NAME = "ghi_chu";
    public static final String COLUMN_TONGGIA_NAME = "tong_gia";
    public static final String COLUMN_TRANGTHAI_NAME = "trang_thai";


    @Id
    @Column(name = COLUMN_ID_NAME, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = COLUMN_MA_NAME)
    private String ma;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_phong")
    private Phong phong;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_voucher")
    private Voucher voucher;

    @Column(name = COLUMN_NGAYDAT_NAME)
    private LocalDateTime ngayDat;

    @Column(name = COLUMN_CHECKIN_NAME)
    private LocalDateTime checkIn;

    @Column(name = COLUMN_CHECKOUT_NAME)
    private LocalDateTime checkOut;

    @Column(name = COLUMN_SONGUOI_NAME)
    private Integer soNguoi;

    @Nationalized
    @Lob
    @Column(name = COLUMN_GHICHU_NAME)
    private String ghiChu;

    @Column(name = COLUMN_TONGGIA_NAME)
    private BigDecimal tongGia;

    @Column(name = "tien_coc", precision = 18)
    private BigDecimal tienCoc;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_hoa_don")
    private HoaDon hoaDon;

    @Column(name = COLUMN_TRANGTHAI_NAME)
    private Integer trangThai;

    @OneToMany(mappedBy = "datPhong")
    private Set<ChiTietDichVu> chiTietDichVus = new LinkedHashSet<>();

    @OneToMany(mappedBy = "datPhong")
    private Set<DichVuSuDung> dichVuSuDungs = new LinkedHashSet<>();
}
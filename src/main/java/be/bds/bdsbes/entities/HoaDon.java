package be.bds.bdsbes.entities;

import lombok.*;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
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
@Table(name = HoaDon.TABLE_NAME)
public class HoaDon {
    public static final String TABLE_NAME = "hoa_don";
    public static final String COLUMN_ID_NAME = "id";
    public static final String COLUMN_NGAYTAO_NAME = "ngay_tao";
    public static final String COLUMN_NGAYTHANHTOAN_NAME = "ngay_thanh_toan";
    public static final String COLUMN_TONGTIEN_NAME = "tong_tien";
    public static final String COLUMN_TRANGTHAI_NAME = "trang_thai";
    public static final String COLUMN_GHICHU_NAME = "ghi_chu";


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = COLUMN_ID_NAME, nullable = false)
    private Long id;

    @Column(name = COLUMN_NGAYTAO_NAME)
    private LocalDate ngayTao;

    @Column(name = COLUMN_NGAYTHANHTOAN_NAME)
    private LocalDate ngayThanhToan;

    @Column(name = COLUMN_TONGTIEN_NAME, precision = 18)
    private BigDecimal tongTien;

    @Column(name = COLUMN_TRANGTHAI_NAME)
    private Integer trangThai;

    @Nationalized
    @Lob
    @Column(name = COLUMN_GHICHU_NAME)
    private String ghiChu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_khach_hang")
    private KhachHang khachHang;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_dat_phong")
    private DatPhong datPhong;

    @OneToMany(mappedBy = "hoaDon")
    private Set<HoaDonChiTiet> hoaDonChiTiets = new LinkedHashSet<>();

}
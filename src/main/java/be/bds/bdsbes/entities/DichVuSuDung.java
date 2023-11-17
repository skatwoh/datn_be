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
@Table(name = DichVuSuDung.TABLE_NAME)
public class DichVuSuDung {
    public static final String TABLE_NAME = "dich_vu_su_dung";
    public static final String COLUMN_ID_NAME = "id";
    public static final String COLUMN_NGAYDAT_NAME = "ngay_dat";
    public static final String COLUMN_NGAYNHAN_NAME = "ngay_nhan";
    public static final String COLUMN_NGAYTRA_NAME = "ngay_tra";
    public static final String COLUMN_GIA_NAME = "gia";
    public static final String COLUMN_GHICHU_NAME = "ghi_chu";
    public static final String COLUMN_TRANGTHAI_NAME = "trang_thai";


    @Id
    @Column(name = COLUMN_ID_NAME, nullable = false)
    private Long id;

    @Column(name = COLUMN_NGAYDAT_NAME)
    private LocalDate ngayDat;

    @Column(name = COLUMN_NGAYNHAN_NAME)
    private LocalDate ngayNhan;

    @Column(name = COLUMN_NGAYTRA_NAME)
    private LocalDate ngayTra;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_khach_hang")
    private KhachHang khachHang;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_dat_phong")
    private DatPhong datPhong;

    @Column(name = COLUMN_GIA_NAME, precision = 18)
    private BigDecimal gia;

    @Nationalized
    @Lob
    @Column(name = COLUMN_GHICHU_NAME)
    private String ghiChu;

    @Column(name = COLUMN_TRANGTHAI_NAME)
    private Integer trangThai;

}
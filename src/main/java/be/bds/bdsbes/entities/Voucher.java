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
@Table(name = Voucher.TABLE_NAME)
public class Voucher {
    public static final String TABLE_NAME = "voucher";
    public static final String COLUMN_ID_NAME = "id";
    public static final String COLUMN_MA_NAME = "ma";
    public static final String COLUMN_MOTA_NAME = "mo_ta";
    public static final String COLUMN_GIAMGIA_NAME = "giam_gia";
    public static final String COLUMN_NGAYBATDAU_NAME = "ngay_bat_dau";
    public static final String COLUMN_NGAYKETTHUC_NAME = "ngay_ket_thuc";
    public static final String COLUMN_TRANGTHAI_NAME = "trang_thai";
    public static final String COLUMN_SOLUONG_NAME = "so_luong";


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = COLUMN_ID_NAME, nullable = false)
    private Long id;

    @Size(max = 20)
    @Column(name = COLUMN_MA_NAME, length = 20)
    private String ma;

    @Nationalized
    @Lob
    @Column(name = COLUMN_MOTA_NAME)
    private String moTa;

    @Column(name = COLUMN_GIAMGIA_NAME, precision = 18)
    private BigDecimal giamGia;

    @Column(name = COLUMN_NGAYBATDAU_NAME)
    private LocalDate ngayBatDau;

    @Column(name = COLUMN_NGAYKETTHUC_NAME)
    private LocalDate ngayKetThuc;

    @Column(name = COLUMN_TRANGTHAI_NAME)
    private Integer trangThai;

    @Column(name = COLUMN_SOLUONG_NAME)
    private Integer soLuong;

    @OneToMany(mappedBy = "voucher")
    private Set<KhachHang> khachHangs = new LinkedHashSet<>();

}
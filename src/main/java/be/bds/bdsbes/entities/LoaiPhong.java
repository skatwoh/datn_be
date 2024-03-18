package be.bds.bdsbes.entities;

import lombok.*;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = LoaiPhong.TABLE_NAME)
public class LoaiPhong {
    public static final String TABLE_NAME = "loai_phong";
    public static final String COLUMN_ID_NAME = "id";
    public static final String COLUMN_MALOAIPHONG_NAME = "ma_loai_phong";
    public static final String COLUMN_TENLOAIPHONG_NAME = "ten_loai_phong";
    public static final String COLUMN_SONGUOI_NAME = "so_nguoi";
    public static final String COLUMN_TIENICH_NAME = "tien_ich";
    public static final String COLUMN_GHICHU_NAME = "ghi_chu";
    public static final String COLUMN_GIATHEONGAY_NAME = "gia_theo_ngay";
    public static final String COLUMN_GIATHEOGIO_NAME = "gia_theo_gio";


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = COLUMN_ID_NAME, nullable = false)
    private Long id;

    @Size(max = 20)
    @Column(name = COLUMN_MALOAIPHONG_NAME, length = 20)
    private String maLoaiPhong;

    @Size(max = 50)
    @Nationalized
    @Column(name = COLUMN_TENLOAIPHONG_NAME, length = 50)
    private String tenLoaiPhong;

    @Column(name = COLUMN_SONGUOI_NAME)
    private int soNguoi;

    @Nationalized
    @Lob
    @Column(name = COLUMN_TIENICH_NAME)
    private String tienIch;

    @Nationalized
    @Lob
    @Column(name = COLUMN_GHICHU_NAME)
    private String ghiChu;

    @Column(name = COLUMN_GIATHEONGAY_NAME)
    private BigDecimal giaTheoNgay;

    @Column(name = COLUMN_GIATHEOGIO_NAME)
    private BigDecimal giaTheoGio;

}
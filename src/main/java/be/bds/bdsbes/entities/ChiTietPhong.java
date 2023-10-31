package be.bds.bdsbes.entities;

import lombok.*;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = ChiTietPhong.TABLE_NAME)
public class ChiTietPhong {
    public static final String TABLE_NAME = "chi_tiet_phong";
    public static final String COLUMN_ID_NAME = "id";
    public static final String COLUMN_TANG_NAME = "tang";
    public static final String COLUMN_TIENICH_NAME = "tien_ich";
    public static final String COLUMN_DICHVU_NAME = "dich_vu";
    public static final String COLUMN_SOLUONGNGUOI_NAME = "so_luong_nguoi";
    public static final String COLUMN_DIENTICH_NAME = "dien_tich";
    public static final String COLUMN_TRANGTHAI_NAME = "trang_thai";


    @Id
    @Column(name = COLUMN_ID_NAME, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_phong")
    private LoaiPhong loaiPhong;

    @Size(max = 20)
    @Column(name = COLUMN_TANG_NAME, length = 20)
    private String tang;

    @Size(max = 100)
    @Nationalized
    @Column(name = COLUMN_TIENICH_NAME, length = 100)
    private String tienIch;

    @Size(max = 100)
    @Nationalized
    @Column(name = COLUMN_DICHVU_NAME, length = 100)
    private String dichVu;

    @Column(name = COLUMN_SOLUONGNGUOI_NAME)
    private Integer soLuongNguoi;

    @Column(name = COLUMN_DIENTICH_NAME)
    private Double dienTich;

    @Column(name = COLUMN_TRANGTHAI_NAME)
    private Integer trangThai;

}
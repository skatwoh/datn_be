package be.bds.bdsbes.entities;

import lombok.*;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = CoSoVatChat.TABLE_NAME)
public class CoSoVatChat {
    public static final String TABLE_NAME = "co_so_vat_chat";
    public static final String COLUMN_ID_NAME = "id";
    public static final String COLUMN_SOLUONG_NAME = "so_luong";
    public static final String COLUMN_GHICHU_NAME = "ghi_chu";
    public static final String COLUMN_TRANGTHAI_NAME = "trang_thai";


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = COLUMN_ID_NAME, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tai_san")
    private TaiSan taiSan;

    @Column(name = COLUMN_SOLUONG_NAME)
    private Integer soLuong;

    @Nationalized
    @Lob
    @Column(name = COLUMN_GHICHU_NAME)
    private String ghiChu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_chi_tiet_phong")
    private ChiTietPhong chiTietPhong;

    @Column(name = COLUMN_TRANGTHAI_NAME)
    private Integer trangThai;

}
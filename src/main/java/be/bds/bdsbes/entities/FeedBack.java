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
@Table(name = FeedBack.TABLE_NAME)
public class FeedBack {
    public static final String TABLE_NAME = "feed_back";
    public static final String COLUMN_ID_NAME = "id";
    public static final String COLUMN_MOTA_NAME = "mo_ta";
    public static final String COLUMN_TRANGTHAI_NAME = "trang_thai";


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = COLUMN_ID_NAME, nullable = false)
    private Long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_chi_tiet_phong")
    private ChiTietPhong chiTietPhong;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_khach_hang")
    private KhachHang khachHang;

    @Nationalized
    @Lob
    @Column(name = COLUMN_MOTA_NAME)
    private String moTa;

    @Column(name = COLUMN_TRANGTHAI_NAME)
    private Integer trangThai;

}
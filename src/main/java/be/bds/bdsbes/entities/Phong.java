package be.bds.bdsbes.entities;

import lombok.*;

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
@Table(name = Phong.TABLE_NAME)
public class Phong {
    public static final String TABLE_NAME = "phong";
    public static final String COLUMN_ID_NAME = "id";
    public static final String COLUMN_MA_NAME = "ma";
    public static final String COLUMN_GIAPHONG_NAME = "gia_phong";
    public static final String COLUMN_TRANGTHAI_NAME = "trang_thai";


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = COLUMN_ID_NAME, nullable = false)
    private Long id;

    @Size(max = 20)
    @Column(name = COLUMN_MA_NAME, length = 20)
    private String ma;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_loai_phong")
    private LoaiPhong loaiPhong;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sale")
    private Sale sale;

    @Column(name = COLUMN_GIAPHONG_NAME, precision = 18)
    private BigDecimal giaPhong;

    @Column(name = COLUMN_TRANGTHAI_NAME)
    private Integer trangThai;

    @Column(name = "image")
    private String image;

    @OneToMany(mappedBy = "phong")
    private Set<ChiTietPhong> chiTietPhongs = new LinkedHashSet<>();

    @OneToMany(mappedBy = "phong")
    private Set<DatPhong> datPhongs = new LinkedHashSet<>();
}
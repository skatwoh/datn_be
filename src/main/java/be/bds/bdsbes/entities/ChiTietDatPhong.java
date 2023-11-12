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
@Table(name = ChiTietDatPhong.TABLE_NAME)
public class ChiTietDatPhong {
    public static final String TABLE_NAME = "chi_tiet_dat_phong";
    public static final String COLUMN_ID_NAME = "id";
    public static final String COLUMN_GHICHU_NAME = "ghi_chu";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = COLUMN_ID_NAME, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_dat_phong")
    private DatPhong datPhong;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_chi_tiet_phong")
    private ChiTietPhong chiTietPhong;

    @Size(max = 100)
    @Nationalized
    @Column(name = COLUMN_GHICHU_NAME, length = 100)
    private String ghiChu;

}
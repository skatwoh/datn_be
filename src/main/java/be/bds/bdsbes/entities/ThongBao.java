package be.bds.bdsbes.entities;

import be.bds.bdsbes.domain.User;
import lombok.*;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = ThongBao.TABLE_NAME)
public class ThongBao {
    public static final String TABLE_NAME = "thong_bao";
    public static final String COLUMN_ID_NAME = "id";
    public static final String COLUMN_MADATPHONG_NAME = "ma_dat_phong";
    public static final String COLUMN_NOIDUNG_NAME = "noi_dung";
    public static final String COLUMN_TIMESTAMP_NAME = "\"timestamp\"";
    public static final String COLUMN_TRANGTHAI_NAME = "trang_thai";


    @Id
    @Column(name = COLUMN_ID_NAME)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Size(max = 50)
    @Nationalized
    @Column(name = COLUMN_MADATPHONG_NAME, length = 50)
    private String maDatPhong;

    @Nationalized
    @Lob
    @Column(name = COLUMN_NOIDUNG_NAME)
    private String noiDung;

    @Column(name = COLUMN_TIMESTAMP_NAME)
    private LocalDateTime timestamp;

    @Column(name = COLUMN_TRANGTHAI_NAME)
    private Integer trangThai;

}
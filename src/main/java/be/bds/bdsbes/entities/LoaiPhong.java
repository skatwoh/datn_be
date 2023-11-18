package be.bds.bdsbes.entities;

import lombok.*;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import javax.validation.constraints.Size;
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
    public static final String COLUMN_GHICHU_NAME = "ghi_chu";


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

    @Nationalized
    @Lob
    @Column(name = COLUMN_GHICHU_NAME)
    private String ghiChu;


}
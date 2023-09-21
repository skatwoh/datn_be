package be.bds.bdsbes.entities;

import lombok.*;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = DichVu.TABLE_NAME)
public class DichVu {
    public static final String TABLE_NAME = "dich_vu";
    public static final String COLUMN_ID_NAME = "id";
    public static final String COLUMN_MA_NAME = "ma";
    public static final String COLUMN_TENDICHVU_NAME = "ten_dich_vu";
    public static final String COLUMN_GHICHU_NAME = "ghi_chu";
    public static final String COLUMN_GIADICHVU_NAME = "gia_dich_vu";
    public static final String COLUMN_TRANGTHAI_NAME = "trang_thai";


    @Id
    @Column(name = COLUMN_ID_NAME, nullable = false)
    private Long id;

    @Size(max = 20)
    @Column(name = COLUMN_MA_NAME, length = 20)
    private String ma;

    @Size(max = 50)
    @Nationalized
    @Column(name = COLUMN_TENDICHVU_NAME, length = 50)
    private String tenDichVu;

    @Nationalized
    @Lob
    @Column(name = COLUMN_GHICHU_NAME)
    private String ghiChu;

    @Column(name = COLUMN_GIADICHVU_NAME, precision = 18)
    private BigDecimal giaDichVu;

    @Column(name = COLUMN_TRANGTHAI_NAME)
    private Integer trangThai;

}
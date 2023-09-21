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
@Table(name = QuanLyDoiTac.TABLE_NAME)
public class QuanLyDoiTac {
    public static final String TABLE_NAME = "quan_ly_doi_tac";
    public static final String COLUMN_ID_NAME = "id";
    public static final String COLUMN_MA_NAME = "ma";
    public static final String COLUMN_TENCONGTY_NAME = "ten_cong_ty";
    public static final String COLUMN_GHICHU_NAME = "ghi_chu";
    public static final String COLUMN_TRANGTHAI_NAME = "trang_thai";


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = COLUMN_ID_NAME, nullable = false)
    private Long id;

    @Size(max = 20)
    @Column(name = COLUMN_MA_NAME, length = 20)
    private String ma;

    @Size(max = 50)
    @Nationalized
    @Column(name = COLUMN_TENCONGTY_NAME, length = 50)
    private String tenCongTy;

    @Nationalized
    @Lob
    @Column(name = COLUMN_GHICHU_NAME)
    private String ghiChu;

    @Column(name = COLUMN_TRANGTHAI_NAME)
    private Integer trangThai;

}
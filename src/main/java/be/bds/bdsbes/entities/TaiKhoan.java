package be.bds.bdsbes.entities;

import lombok.*;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = TaiKhoan.TABLE_NAME)
public class TaiKhoan implements Serializable {
    public static final String TABLE_NAME = "tai_khoan";
    public static final String COLUMN_ID_NAME = "id";
    public static final String COLUMN_EMAIL_NAME = "email";
    public static final String COLUMN_MATKHAU_NAME = "mat_khau";
    public static final String COLUMN_TRANGTHAI_NAME = "trang_thai";
    public static final String COLUMN_TEN_NAME = "ten";


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = COLUMN_ID_NAME, nullable = false)
    private Long id;

    @Size(max = 50)
    @Column(name = COLUMN_EMAIL_NAME, length = 50)
    private String email;

    @Size(max = 50)
    @Nationalized
    @Column(name = COLUMN_MATKHAU_NAME, length = 50)
    private String matKhau;

    @Column(name = COLUMN_TRANGTHAI_NAME)
    private Integer trangThai;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_khach_hang")
    private KhachHang khachHang;

    @Size(max = 50)
    @Nationalized
    @Column(name = COLUMN_TEN_NAME, length = 50)
    private String ten;

}
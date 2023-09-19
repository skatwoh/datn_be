package be.bds.bdsbes.entities;

import lombok.*;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "khach_hang")
public class KhachHang {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Size(max = 20)
    @Column(name = "ma", length = 20)
    private String ma;

    @Size(max = 50)
    @Nationalized
    @Column(name = "ho_ten", length = 50)
    private String hoTen;

    @Column(name = "ngay_sinh")
    private LocalDate ngaySinh;

    @Column(name = "gioi_tinh")
    private Boolean gioiTinh;

    @Size(max = 100)
    @Nationalized
    @Column(name = "dia_chi", length = 100)
    private String diaChi;

    @Size(max = 11)
    @Column(name = "sdt", length = 11)
    private String sdt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tai_khoan")
    private TaiKhoan idTaiKhoan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_the_thanh_vien")
    private TheThanhVien idTheThanhVien;

    @Nationalized
    @Lob
    @Column(name = "ghi_chu")
    private String ghiChu;

}
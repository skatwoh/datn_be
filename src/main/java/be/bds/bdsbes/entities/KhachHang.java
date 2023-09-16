package be.bds.bdsbes.entities;

import lombok.*;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class KhachHang {
    @Id
    @Column(name = "Id", nullable = false)
    private UUID id;

    @Size(max = 20)
    @Column(name = "Ma", length = 20)
    private String ma;

    @Size(max = 50)
    @Nationalized
    @Column(name = "HoTen", length = 50)
    private String hoTen;

    @Column(name = "NgaySinh")
    private LocalDate ngaySinh;

    @Column(name = "GioiTinh")
    private Boolean gioiTinh;

    @Size(max = 100)
    @Nationalized
    @Column(name = "DiaChi", length = 100)
    private String diaChi;

    @Size(max = 11)
    @Column(name = "SDT", length = 11)
    private String sdt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdTaiKhoan")
    private TaiKhoan idTaiKhoan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdTheThanhVien")
    private TheThanhVien idTheThanhVien;

    @Nationalized
    @Lob
    @Column(name = "GhiChu")
    private String ghiChu;

    @OneToMany(mappedBy = "idKhachHang")
    private Set<DatPhong> datPhongs = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idKhachHang")
    private Set<Feedback> feedbacks = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idKhachHang")
    private Set<HoaDon> hoaDons = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idKhachHang")
    private Set<LichSuDatPhong> lichSuDatPhongs = new LinkedHashSet<>();

}
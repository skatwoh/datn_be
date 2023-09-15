package be.bds.bdsbes.entities;

import lombok.*;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class DatPhong {
    @Id
    @Column(name = "Id", nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_KhachHang")
    private KhachHang idKhachhang;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_Voucher")
    private Voucher idVoucher;

    @Column(name = "NgayDat")
    private LocalDate ngayDat;

    @Column(name = "CheckIn")
    private LocalDate checkIn;

    @Column(name = "CheckOut")
    private LocalDate checkOut;

    @Column(name = "SoNguoi")
    private Integer soNguoi;

    @Nationalized
    @Lob
    @Column(name = "GhiChu")
    private String ghiChu;

    @Column(name = "TrangThai")
    private Integer trangThai;

    @OneToMany(mappedBy = "idDatphong")
    private Set<ChiTietDatPhong> chiTietDatPhongs = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idDatphong")
    private Set<ChiTietDichVu> chiTietDichVus = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idDatphong")
    private Set<HoaDon> hoaDons = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idDatphong")
    private Set<LichSuDatPhong> lichSuDatPhongs = new LinkedHashSet<>();

}
package be.bds.bdsbes.entities;

import lombok.*;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.math.BigDecimal;
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
public class HoaDon {
    @Id
    @Column(name = "Id", nullable = false)
    private UUID id;

    @Column(name = "NgayTao")
    private LocalDate ngayTao;

    @Column(name = "NgayThanhToan")
    private LocalDate ngayThanhToan;

    @Column(name = "TongTien", precision = 18)
    private BigDecimal tongTien;

    @Column(name = "TrangThai")
    private Integer trangThai;

    @Nationalized
    @Lob
    @Column(name = "GhiChu")
    private String ghiChu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_KhachHang")
    private KhachHang idKhachhang;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_DatPhong")
    private DatPhong idDatphong;

    @OneToMany(mappedBy = "idHoadon")
    private Set<HoaDonChiTiet> hoaDonChiTiets = new LinkedHashSet<>();

}
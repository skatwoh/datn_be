package be.bds.bdsbes.entities;

import lombok.*;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class LichSuDatPhong {
    @Id
    @Column(name = "Id", nullable = false)
    private UUID id;

    @Column(name = "NgayDat")
    private LocalDate ngayDat;

    @Column(name = "NgayNhan")
    private LocalDate ngayNhan;

    @Column(name = "NgayTra")
    private LocalDate ngayTra;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_KhachHang")
    private KhachHang idKhachhang;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_DatPhong")
    private DatPhong idDatphong;

    @Column(name = "Gia", precision = 18)
    private BigDecimal gia;

    @Nationalized
    @Lob
    @Column(name = "GhiChu")
    private String ghiChu;

    @Column(name = "TrangThai")
    private Integer trangThai;

}
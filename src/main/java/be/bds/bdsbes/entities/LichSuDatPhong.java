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
@ToString
@Entity
@Table(name = "lich_su_dat_phong")
public class LichSuDatPhong {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "ngay_dat")
    private LocalDate ngayDat;

    @Column(name = "ngay_nhan")
    private LocalDate ngayNhan;

    @Column(name = "ngay_tra")
    private LocalDate ngayTra;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_khach_hang")
    private KhachHang idKhachHang;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_dat_phong")
    private DatPhong idDatPhong;

    @Column(name = "gia", precision = 18)
    private BigDecimal gia;

    @Nationalized
    @Lob
    @Column(name = "ghi_chu")
    private String ghiChu;

    @Column(name = "trang_thai")
    private Integer trangThai;

}
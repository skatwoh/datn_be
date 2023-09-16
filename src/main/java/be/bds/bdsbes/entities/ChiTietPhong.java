package be.bds.bdsbes.entities;

import lombok.*;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import javax.validation.constraints.Size;
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
public class ChiTietPhong {
    @Id
    @Column(name = "Id", nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdLoaiPhong")
    private LoaiPhong idLoaiPhong;

    @Size(max = 20)
    @Column(name = "Tang", length = 20)
    private String tang;

    @Size(max = 100)
    @Nationalized
    @Column(name = "TienIch", length = 100)
    private String tienIch;

    @Size(max = 100)
    @Nationalized
    @Column(name = "DichVu", length = 100)
    private String dichVu;

    @Column(name = "SoLuongNguoi")
    private Integer soLuongNguoi;

    @Column(name = "DienTich")
    private Double dienTich;

    @Column(name = "TrangThai")
    private Integer trangThai;

    @OneToMany(mappedBy = "idChiTietPhong")
    private Set<BaoTri> baoTris = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idChiTietPhong")
    private Set<ChiTietDatPhong> chiTietDatPhongs = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idChiTietPhong")
    private Set<CoSoVatChat> coSoVatChats = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idChiTietPhong")
    private Set<Feedback> feedbacks = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idChiTietPhong")
    private Set<HoaDonChiTiet> hoaDonChiTiets = new LinkedHashSet<>();

}
package be.bds.bdsbes.entities;

import lombok.*;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "co_so_vat_chat")
public class CoSoVatChat {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tai_san")
    private TaiSan idTaiSan;

    @Column(name = "so_luong")
    private Integer soLuong;

    @Nationalized
    @Lob
    @Column(name = "ghi_chu")
    private String ghiChu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_chi_tiet_phong")
    private ChiTietPhong idChiTietPhong;

    @Column(name = "trang_thai")
    private Integer trangThai;

}
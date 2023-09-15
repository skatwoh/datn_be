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
@Entity
public class CoSoVatChat {
    @Id
    @Column(name = "Id", nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_TaiSan")
    private TaiSan idTaisan;

    @Column(name = "SoLuong")
    private Integer soLuong;

    @Nationalized
    @Lob
    @Column(name = "GhiChu")
    private String ghiChu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_ChiTietPhong")
    private ChiTietPhong idChitietphong;

    @Column(name = "TrangThai")
    private Integer trangThai;

}
package be.bds.bdsbes.entities;

import lombok.*;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class HoaDonChiTiet {
    @Id
    @Column(name = "Id", nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_HoaDon")
    private HoaDon idHoadon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_ChiTietPhong")
    private ChiTietPhong idChitietphong;

    @Column(name = "Gia", precision = 18)
    private BigDecimal gia;

    @Size(max = 100)
    @Nationalized
    @Column(name = "GhiChu", length = 100)
    private String ghiChu;

    @Column(name = "TrangThai")
    private Integer trangThai;

}
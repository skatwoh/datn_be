package be.bds.bdsbes.entities;

import lombok.*;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class ChiTietDichVu {
    @Id
    @Column(name = "Id", nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_DichVu")
    private DichVu idDichvu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_DatPhong")
    private DatPhong idDatphong;

    @Nationalized
    @Lob
    @Column(name = "GhiChu")
    private String ghiChu;

    @Column(name = "GiaDichVu", precision = 18)
    private BigDecimal giaDichVu;

    @Column(name = "TrangThai")
    private Integer trangThai;

}
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
@ToString
@Entity
public class ChiTietDichVu {
    @Id
    @Column(name = "Id", nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdDichVu")
    private DichVu idDichVu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdDatPhong")
    private DatPhong idDatPhong;

    @Nationalized
    @Lob
    @Column(name = "GhiChu")
    private String ghiChu;

    @Column(name = "GiaDichVu", precision = 18)
    private BigDecimal giaDichVu;

    @Column(name = "TrangThai")
    private Integer trangThai;

}
package be.bds.bdsbes.entities;

import lombok.*;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class ChiTietDatPhong {
    @Id
    @Column(name = "Id", nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdDatPhong")
    private DatPhong idDatPhong;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdChiTietPhong")
    private ChiTietPhong idChiTietPhong;

    @Size(max = 100)
    @Nationalized
    @Column(name = "GhiChu", length = 100)
    private String ghiChu;

}
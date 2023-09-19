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
@Table(name = "chi_tiet_dat_phong")
public class ChiTietDatPhong {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_dat_phong")
    private DatPhong idDatPhong;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_chi_tiet_phong")
    private ChiTietPhong idChiTietPhong;

    @Size(max = 100)
    @Nationalized
    @Column(name = "ghi_chu", length = 100)
    private String ghiChu;

}
package be.bds.bdsbes.entities;

import lombok.*;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class DichVu {
    @Id
    @Column(name = "Id", nullable = false)
    private UUID id;

    @Size(max = 20)
    @Column(name = "Ma", length = 20)
    private String ma;

    @Size(max = 50)
    @Nationalized
    @Column(name = "TenDichVu", length = 50)
    private String tenDichVu;

    @Nationalized
    @Lob
    @Column(name = "GhiChu")
    private String ghiChu;

    @Column(name = "GiaDichVu", precision = 18)
    private BigDecimal giaDichVu;

    @Column(name = "TrangThai")
    private Integer trangThai;

    @OneToMany(mappedBy = "idDichvu")
    private Set<ChiTietDichVu> chiTietDichVus = new LinkedHashSet<>();

}
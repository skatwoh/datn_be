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
@ToString
@Entity
@Table(name = "dich_vu")
public class DichVu {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Size(max = 20)
    @Column(name = "ma", length = 20)
    private String ma;

    @Size(max = 50)
    @Nationalized
    @Column(name = "ten_dich_vu", length = 50)
    private String tenDichVu;

    @Nationalized
    @Lob
    @Column(name = "ghi_chu")
    private String ghiChu;

    @Column(name = "gia_dich_vu", precision = 18)
    private BigDecimal giaDichVu;

    @Column(name = "trang_thai")
    private Integer trangThai;

    @OneToMany(mappedBy = "idDichVu")
    private Set<ChiTietDichVu> chiTietDichVus = new LinkedHashSet<>();

}
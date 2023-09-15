package be.bds.bdsbes.entities;

import lombok.*;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class TheThanhVien {
    @Id
    @Column(name = "Id", nullable = false)
    private UUID id;

    @Size(max = 20)
    @Column(name = "Ma", length = 20)
    private String ma;

    @Size(max = 50)
    @Nationalized
    @Column(name = "CAPBAC", length = 50)
    private String capbac;

    @Column(name = "NgayHetHan")
    private LocalDate ngayHetHan;

    @Column(name = "GiamGia", precision = 18)
    private BigDecimal giamGia;

    @Nationalized
    @Lob
    @Column(name = "GhiChu")
    private String ghiChu;

    @OneToMany(mappedBy = "idThethanhvien")
    private Set<KhachHang> khachHangs = new LinkedHashSet<>();

}
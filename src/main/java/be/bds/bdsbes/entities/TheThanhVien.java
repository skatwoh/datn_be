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
@ToString
@Entity
@Table(name = "the_thanh_vien")
public class TheThanhVien {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Size(max = 20)
    @Column(name = "ma", length = 20)
    private String ma;

    @Size(max = 50)
    @Nationalized
    @Column(name = "cap_bac", length = 50)
    private String capBac;

    @Column(name = "ngay_het_han")
    private LocalDate ngayHetHan;

    @Column(name = "giam_gia", precision = 18)
    private BigDecimal giamGia;

    @Nationalized
    @Lob
    @Column(name = "ghi_chu")
    private String ghiChu;

    @OneToMany(mappedBy = "idTheThanhVien")
    private Set<KhachHang> khachHangs = new LinkedHashSet<>();

}
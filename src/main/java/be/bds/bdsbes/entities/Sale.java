package be.bds.bdsbes.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "sale")
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 50)
    @Nationalized
    @Column(name = "ma", length = 50)
    private String ma;

    @Column(name = "ten", length = 100)
    private String ten;

    @Column(name = "gia_tri", precision = 18)
    private BigDecimal giaTri;

    @Column(name = "ngay_bat_dau")
    private LocalDateTime ngayBatDau;

    @Column(name = "ngay_ket_thuc")
    private LocalDateTime ngayKetThuc;

    @Column(name = "trang_thai")
    private Integer trangThai;

    @OneToMany(mappedBy = "sale")
    private Set<Phong> phongs = new LinkedHashSet<>();

}
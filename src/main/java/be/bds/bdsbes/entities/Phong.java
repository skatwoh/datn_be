package be.bds.bdsbes.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "phong")
public class Phong {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Size(max = 20)
    @Column(name = "ma", length = 20)
    private String ma;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_loai_phong")
    private LoaiPhong idLoaiPhong;

    @Column(name = "gia_phong", precision = 18)
    private BigDecimal giaPhong;

    @Column(name = "trang_thai")
    private Integer trangThai;

}
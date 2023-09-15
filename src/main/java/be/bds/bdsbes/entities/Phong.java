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
@Entity
public class Phong {
    @Id
    @Column(name = "Id", nullable = false)
    private UUID id;

    @Size(max = 20)
    @Column(name = "Ma", length = 20)
    private String ma;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_LoaiPhong")
    private LoaiPhong idLoaiphong;

    @Column(name = "GiaPhong", precision = 18)
    private BigDecimal giaPhong;

    @Column(name = "TrangThai")
    private Integer trangThai;

}
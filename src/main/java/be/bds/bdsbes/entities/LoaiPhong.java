package be.bds.bdsbes.entities;

import lombok.*;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class LoaiPhong {
    @Id
    @Column(name = "Id", nullable = false)
    private UUID id;

    @Size(max = 20)
    @Column(name = "MaLoaiPhong", length = 20)
    private String maLoaiPhong;

    @Size(max = 50)
    @Nationalized
    @Column(name = "TenLoaiPhong", length = 50)
    private String tenLoaiPhong;

    @Nationalized
    @Lob
    @Column(name = "GhiChu")
    private String ghiChu;

    @OneToMany(mappedBy = "idLoaiphong")
    private Set<ChiTietPhong> chiTietPhongs = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idLoaiphong")
    private Set<Phong> phongs = new LinkedHashSet<>();

}
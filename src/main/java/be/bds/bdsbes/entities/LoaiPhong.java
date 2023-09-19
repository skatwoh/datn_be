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
@ToString
@Entity
@Table(name = "loai_phong")
public class LoaiPhong {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Size(max = 20)
    @Column(name = "ma_loai_phong", length = 20)
    private String maLoaiPhong;

    @Size(max = 50)
    @Nationalized
    @Column(name = "ten_loai_phong", length = 50)
    private String tenLoaiPhong;

    @Nationalized
    @Lob
    @Column(name = "ghi_chu")
    private String ghiChu;

    @OneToMany(mappedBy = "idLoaiPhong")
    private Set<ChiTietPhong> chiTietPhongs = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idLoaiPhong")
    private Set<Phong> phongs = new LinkedHashSet<>();

}
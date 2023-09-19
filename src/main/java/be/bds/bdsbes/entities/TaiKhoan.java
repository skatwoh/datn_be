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
@Table(name = "tai_khoan")
public class TaiKhoan {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Size(max = 50)
    @Column(name = "email", length = 50)
    private String email;

    @Size(max = 50)
    @Nationalized
    @Column(name = "mat_khau", length = 50)
    private String matKhau;

    @Column(name = "trang_thai")
    private Integer trangThai;

    @OneToMany(mappedBy = "idTaiKhoan")
    private Set<KhachHang> khachHangs = new LinkedHashSet<>();

}
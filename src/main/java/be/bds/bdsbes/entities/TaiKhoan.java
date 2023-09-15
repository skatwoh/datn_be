package be.bds.bdsbes.entities;

import lombok.*;
import org.hibernate.annotations.Nationalized;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
public class TaiKhoan {
    @Id
    @Column(name = "Id", nullable = false)
    private UUID id;

    @Size(max = 50)
    @Column(name = "Email", length = 50)
    private String email;

    @Size(max = 50)
    @Nationalized
    @Column(name = "MatKhau", length = 50)
    private String matKhau;

    @Column(name = "TrangThai")
    private Integer trangThai;

    @OneToMany(mappedBy = "idTaikhoan")
    private Set<KhachHang> khachHangs = new LinkedHashSet<>();

}
package be.bds.bdsbes.entities;

import lombok.*;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "chi_tiet_phong")
public class ChiTietPhong {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_loai_phong")
    private LoaiPhong idLoaiPhong;

    @Size(max = 20)
    @Column(name = "tang", length = 20)
    private String tang;

    @Size(max = 100)
    @Nationalized
    @Column(name = "tien_ich", length = 100)
    private String tienIch;

    @Size(max = 100)
    @Nationalized
    @Column(name = "dich_vu", length = 100)
    private String dichVu;

    @Column(name = "so_luong_nguoi")
    private Integer soLuongNguoi;

    @Column(name = "dien_tich")
    private Double dienTich;

    @Column(name = "trang_thai")
    private Integer trangThai;

}
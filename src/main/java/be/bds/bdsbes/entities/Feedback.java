package be.bds.bdsbes.entities;

import lombok.*;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Feedback {
    @Id
    @Column(name = "Id", nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_ChiTietPhong")
    private ChiTietPhong idChitietphong;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Id_KhachHang")
    private KhachHang idKhachhang;

    @Nationalized
    @Lob
    @Column(name = "MoTa")
    private String moTa;

    @Column(name = "TrangThai")
    private Integer trangThai;

}
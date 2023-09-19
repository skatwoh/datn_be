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
@Table(name = "quan_ly_doi_tac")
public class QuanLyDoiTac {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Size(max = 20)
    @Column(name = "ma", length = 20)
    private String ma;

    @Size(max = 50)
    @Nationalized
    @Column(name = "ten_cong_ty", length = 50)
    private String tenCongTy;

    @Nationalized
    @Lob
    @Column(name = "ghi_chu")
    private String ghiChu;

    @Column(name = "trang_thai")
    private Integer trangThai;

}
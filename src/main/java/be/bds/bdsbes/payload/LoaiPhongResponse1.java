package be.bds.bdsbes.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoaiPhongResponse1 {

    private Long id;

    private String maLoaiPhong;

    private String tenLoaiPhong;

    private String ghiChu;
}

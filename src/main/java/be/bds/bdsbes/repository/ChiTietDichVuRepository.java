package be.bds.bdsbes.repository;

import be.bds.bdsbes.entities.ChiTietDichVu;
import be.bds.bdsbes.service.dto.response.ChiTietDichVuResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ChiTietDichVuRepository extends JpaRepository<ChiTietDichVu, Long> {
    @Query(value = "select ctdv.id, ctdv.id_dich_vu, ctdv.id_dat_phong, ctdv.ghi_chu, ctdv.gia_dich_vu, ctdv.trang_thai from" +
            "chi_tiet_dich_vu ctdv left join dich_vu dv on ctdv.id_dich_vu = dv.id" +
            "chi_tiet_dich_vu left join dat_phong dp on ctdv.id_dat_phong = dp.id", nativeQuery = true)
    List<ChiTietDichVuResponse> getAllChiTietDichVu();
}
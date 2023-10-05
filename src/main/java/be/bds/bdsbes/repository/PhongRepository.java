package be.bds.bdsbes.repository;

import be.bds.bdsbes.entities.Phong;
import be.bds.bdsbes.service.dto.response.PhongResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PhongRepository extends JpaRepository<Phong, Long> {

    @Query(value = "select p.id, p.ma, p.id_loai_phong, l.ma_loai_phong, l.ten_loai_phong, p.gia_phong, p.trang_thai from phong p inner join loai_phong l on p.id_loai_phong = l.id", nativeQuery = true)
    List<PhongResponse> getAllPhong();
}
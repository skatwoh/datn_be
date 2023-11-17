package be.bds.bdsbes.repository;

import be.bds.bdsbes.entities.LoaiPhong;
import be.bds.bdsbes.payload.LoaiPhongResponse1;
import be.bds.bdsbes.service.dto.response.LoaiPhongResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoaiPhongRepository extends JpaRepository<LoaiPhong, Long> {

    @Query(value = "select l.id, l.ma_loai_phong, l.ten_loai_phong from loai_phong l", nativeQuery = true)
    List<LoaiPhongResponse> singleListRoomType();

    @Query("select new be.bds.bdsbes.payload.LoaiPhongResponse1(l.id, l.maLoaiPhong, l.tenLoaiPhong, l.ghiChu) from LoaiPhong l where l.id = :id")
    LoaiPhongResponse1 get(Long id);

//    @Transactional
//    @Modifying
//    @Query("UPDATE LoaiPhong p SET p.trangThai = :trangThai WHERE p.id = :id")
//    Integer updateTrangThaiById(int trangThai, Long id);
}
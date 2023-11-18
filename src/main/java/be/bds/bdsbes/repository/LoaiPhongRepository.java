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

    @Query(value = "select new be.bds.bdsbes.payload.LoaiPhongResponse1(l.id, l.maLoaiPhong, l.tenLoaiPhong, l.ghiChu) from LoaiPhong l")
    List<LoaiPhongResponse1> singleListRoomType();

    @Query("select new be.bds.bdsbes.payload.LoaiPhongResponse1(l.id, l.maLoaiPhong, l.tenLoaiPhong, l.ghiChu) from LoaiPhong l where l.id = :id")
    LoaiPhongResponse1 get(Long id);

//    @Transactional
//    @Modifying
//    @Query("UPDATE LoaiPhong p SET p.trangThai = :trangThai WHERE p.id = :id")
//    Integer updateTrangThaiById(int trangThai, Long id);
}
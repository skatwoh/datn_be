package be.bds.bdsbes.repository;

import be.bds.bdsbes.entities.LoaiPhong;
import be.bds.bdsbes.payload.LoaiPhongResponse1;
import be.bds.bdsbes.service.dto.response.LoaiPhongResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LoaiPhongRepository extends JpaRepository<LoaiPhong, Long> {

    @Query(value = "select l.id, l.ma_loai_phong, l.ten_loai_phong from loai_phong l", nativeQuery = true)
    List<LoaiPhongResponse> singleListRoomType();
}
package be.bds.bdsbes.repository;

import be.bds.bdsbes.entities.FeedBack;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FeedbackRepository extends JpaRepository<FeedBack, Long> {

    @Query("select fb from FeedBack fb join ChiTietPhong ctp on fb.chiTietPhong.id = ctp.id where ctp.id = :id")
    Page<FeedBack> listAll(Pageable pageable, @Param("id") Long id);

    @Query("select count(fb) from FeedBack fb")
    long count();
 }
package be.bds.bdsbes.repository;

import be.bds.bdsbes.entities.ThongBao;
import be.bds.bdsbes.service.dto.response.ThongBaoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThongBaoRepository extends JpaRepository<ThongBao, Long> {

    @Query("select t.noiDung from ThongBao t where t.user.id = :id")
    List<ThongBaoResponse> getAllThongBao(@Param("id") Long id);

    @Query("select t from ThongBao t join t.user u where u.id = :id")
    Page<ThongBao> listAll(Pageable pageable, @Param("id") Long id);
}

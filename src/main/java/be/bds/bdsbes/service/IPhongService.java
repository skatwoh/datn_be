package be.bds.bdsbes.service;

import be.bds.bdsbes.entities.Phong;
import be.bds.bdsbes.exception.ServiceException;
import be.bds.bdsbes.payload.PhongResponse1;
import be.bds.bdsbes.service.dto.PhongDTO;
import be.bds.bdsbes.service.dto.response.PhongResponse;
import be.bds.bdsbes.utils.dto.PagedResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IPhongService {

    List<Phong> getList();

    PagedResponse<PhongResponse1> searchRoom(int page, int size, String searchInput) throws ServiceException;
    PhongResponse1 get(Long id);

    Phong create(PhongDTO phongDTO);

    Phong update(PhongDTO phongDTO, Long id);

    Integer updateTrangThai(Long id);

    PagedResponse<PhongResponse1> getPhong(int page, int size) throws ServiceException;

    PagedResponse<PhongResponse1> getPhongSortbyId(int page, int size) throws ServiceException;
}

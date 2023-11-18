package be.bds.bdsbes.service;

import be.bds.bdsbes.entities.Phong;
import be.bds.bdsbes.exception.ServiceException;
import be.bds.bdsbes.payload.PhongResponse1;
import be.bds.bdsbes.service.dto.PhongDTO;
import be.bds.bdsbes.service.dto.response.PhongResponse;
import be.bds.bdsbes.utils.dto.PagedResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface IPhongService {

    List<Phong> getList();

    PagedResponse<PhongResponse1> searchRoom(int page, int size, String searchInput) throws ServiceException;
    PhongResponse1 get(Long id);

    Phong create(PhongDTO phongDTO) throws ServiceException;

    Phong update(PhongDTO phongDTO, Long id);

    Integer updateTrangThai(Long id);

    PagedResponse<PhongResponse1> getPhong(int page, int size) throws ServiceException;
    PagedResponse<PhongResponse1> getListRoom(int page, int size) throws ServiceException;

    PagedResponse<PhongResponse1> getPhongSortbyId(int page, int size) throws ServiceException;

    List<PhongResponse1> singleListRoom();

    PagedResponse<PhongResponse1> searchRoomManager(int page, int size, String tenLoaiPhong, LocalDateTime checkIn, LocalDateTime checkOut) throws ServiceException;

    PagedResponse<PhongResponse1> searchRoomManager2(int page, int size, String tenLoaiPhong);
}

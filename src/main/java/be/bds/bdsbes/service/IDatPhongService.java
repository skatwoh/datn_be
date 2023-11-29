package be.bds.bdsbes.service;


import be.bds.bdsbes.entities.DatPhong;
import be.bds.bdsbes.exception.ServiceException;
import be.bds.bdsbes.payload.PhongResponse1;
import be.bds.bdsbes.payload.TaiKhoanResponse1;
import be.bds.bdsbes.service.dto.DatPhongDTO;
import be.bds.bdsbes.service.dto.response.DatPhongResponse;
import be.bds.bdsbes.service.dto.response.PhongResponse;
import be.bds.bdsbes.utils.dto.PagedResponse;

import java.math.BigDecimal;
import java.util.List;

public interface IDatPhongService {

    List<DatPhong> getList();

    List<DatPhongResponse> getAll();
    DatPhongResponse getOne(Long id);
    Boolean create(DatPhongDTO datPhongDTO) throws ServiceException;

    DatPhong update(DatPhongDTO datPhongDTO, Long id);

    PagedResponse<DatPhongResponse> getRoomOrder(int page, int size) throws ServiceException;

    PagedResponse<DatPhongResponse> getRoomOderByUser(int page, int size, Long id, Integer trangThai) throws ServiceException;

    PagedResponse<PhongResponse1> getPhongByUpperPrice(int page, int size, BigDecimal giaPhong, Long id) throws ServiceException;

    Integer updateTrangThai(Long id) throws ServiceException;

}

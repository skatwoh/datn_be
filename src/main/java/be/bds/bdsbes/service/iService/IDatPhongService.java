package be.bds.bdsbes.service.iService;


import be.bds.bdsbes.entities.DatPhong;
import be.bds.bdsbes.exception.ServiceException;
import be.bds.bdsbes.payload.PhongResponse1;
import be.bds.bdsbes.payload.TaiKhoanResponse1;
import be.bds.bdsbes.service.dto.DatPhongDTO;
import be.bds.bdsbes.service.dto.KhachHangDTO;
import be.bds.bdsbes.service.dto.response.DatPhongResponse;
import be.bds.bdsbes.service.dto.response.PhongResponse;
import be.bds.bdsbes.utils.dto.PagedResponse;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface IDatPhongService {

    List<DatPhong> getList();

    List<DatPhongResponse> getAll();
    DatPhongResponse getOne(Long id);
    Boolean create(DatPhongDTO datPhongDTO) throws ServiceException;

    DatPhong update(DatPhongDTO datPhongDTO, Long id);

    PagedResponse<DatPhongResponse> getRoomOrder(int page, int size) throws ServiceException;

    PagedResponse<DatPhongResponse> getRoomOderByUser(int page, int size, Long id, Integer trangThai) throws ServiceException;

    PagedResponse<PhongResponse1> getPhongByUpperPrice(int page, int size, BigDecimal giaPhong, Long id, LocalDateTime checkIn, LocalDateTime checkOut) throws ServiceException;

    Integer updateTrangThai(Long id) throws ServiceException;

    Integer updateStatus(Integer trangThai, Long id) throws ServiceException;

    Integer updateDatPhong(Long id, DatPhongDTO datPhongDTO) throws ServiceException;

    PagedResponse<DatPhongResponse> getLichSuDatPhong(int page, int size, Long id) throws ServiceException;

    PagedResponse<DatPhongResponse> getRoomOfBill(int page, int size, Long userId) throws ServiceException;
    Boolean createListRoom(List<DatPhongDTO> datPhongDTOList) throws ServiceException;

    PagedResponse<DatPhongResponse> getDatPhongByHoaDon(int page, int size, Long id) throws ServiceException;

    Boolean datPhongTaiQuay(DatPhongDTO datPhongDTO) throws ServiceException;

    int getSoPhongDaDat(LocalDate CheckIn, LocalDate CheckOut);

    PagedResponse<DatPhongResponse> getDatPhongByKH(int page, int size, Long id) throws ServiceException;

}

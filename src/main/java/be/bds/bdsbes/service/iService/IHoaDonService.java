package be.bds.bdsbes.service.iService;

import be.bds.bdsbes.exception.ServiceException;
import be.bds.bdsbes.payload.HoaDonResponse;
import be.bds.bdsbes.service.dto.HoaDonDTO;
import be.bds.bdsbes.utils.dto.PagedResponse;

public interface IHoaDonService {

    PagedResponse<HoaDonResponse> getHoaDon(int page, int size) throws ServiceException;

    PagedResponse<HoaDonResponse> getHoaDonBySearch(int page, int size, String searchInput) throws ServiceException;


    PagedResponse<HoaDonResponse> getHoaDonByCustomer(int page, int size, String hoTen, String sdt) throws ServiceException;

    Boolean create(HoaDonDTO hoaDonDTO) throws ServiceException;

    Boolean update(HoaDonDTO hoaDonDTO, Long id) throws ServiceException;

    Boolean createOrUpdate(HoaDonDTO hoaDonDTO) throws ServiceException;

    Boolean updateTongTien(HoaDonDTO hoaDonDTO);

    HoaDonResponse getOne(Long id);

    Integer updateTrangThai(Integer trangThai, Long id) throws ServiceException;

    Boolean deleteHoaDon(HoaDonDTO hoaDonDTO);

    Boolean createTaiQuay(HoaDonDTO hoaDonDTO) throws ServiceException;

    Boolean updateTaiQuay(HoaDonDTO hoaDonDTO, Long id) throws ServiceException;

    Boolean createOrUpdateTaiQuay(HoaDonDTO hoaDonDTO) throws ServiceException;
}

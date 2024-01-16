package be.bds.bdsbes.service.iService;

import be.bds.bdsbes.entities.HoaDon;
import be.bds.bdsbes.entities.KhachHang;
import be.bds.bdsbes.exception.ServiceException;
import be.bds.bdsbes.payload.HoaDonResponse;
import be.bds.bdsbes.service.dto.HoaDonDTO;
import be.bds.bdsbes.utils.dto.PagedResponse;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface IHoaDonService {

    PagedResponse<HoaDonResponse> getHoaDon(int page, int size) throws ServiceException;

    PagedResponse<HoaDonResponse> getHoaDonBySearch(int page, int size, String searchInput) throws ServiceException;


    PagedResponse<HoaDonResponse> getHoaDonByCustomer(int page, int size, String hoTen, String sdt) throws ServiceException;

    Boolean create(HoaDonDTO hoaDonDTO) throws ServiceException;

    Boolean update(HoaDonDTO hoaDonDTO, Long id) throws ServiceException;

    Boolean updateTienDichVu(BigDecimal tienDichVu, Long id) throws ServiceException;

    Boolean createOrUpdate(HoaDonDTO hoaDonDTO) throws ServiceException;

    Boolean updateTongTien(HoaDonDTO hoaDonDTO);

    HoaDonResponse getOne(Long id);

    Integer updateTrangThai(Integer trangThai, Long id) throws ServiceException;

    Boolean deleteHoaDon(HoaDonDTO hoaDonDTO);

    Boolean createTaiQuay(HoaDonDTO hoaDonDTO) throws ServiceException;

    Boolean updateTaiQuay(HoaDonDTO hoaDonDTO, Long id) throws ServiceException;

    Boolean createOrUpdateTaiQuay(HoaDonDTO hoaDonDTO) throws ServiceException;

    BigDecimal getDoanhThuByDay(LocalDate checkIn, LocalDate checkOut);

    BigDecimal getDoanhThuByToDay(int day, int month, int year);

    BigDecimal getDoanhThuByMonth(int month, int year);

    BigDecimal getDoanhThuByYear(int year);

    BigDecimal getAllDoanhThu();

    Integer updateRankKhachHang(Long id , Long idTheThanhVien);

    BigDecimal getTongTienByKhachHang(Long id);

    PagedResponse<HoaDonResponse> findBillByCustomer(int page, int size, Long id);
}

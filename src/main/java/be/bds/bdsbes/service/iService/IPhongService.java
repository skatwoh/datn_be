package be.bds.bdsbes.service.iService;

import be.bds.bdsbes.entities.Phong;
import be.bds.bdsbes.exception.ServiceException;
import be.bds.bdsbes.payload.PhongResponse1;
import be.bds.bdsbes.payload.RoomMappingChiTietPhong;
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
    PagedResponse<PhongResponse1> getListSameRoom(int page, int size, Long idPhong) throws ServiceException;
    PagedResponse<PhongResponse1> getListRoom(int page, int size) throws ServiceException;

    PagedResponse<PhongResponse1> getPhongSortbyId(int page, int size) throws ServiceException;

    List<PhongResponse1> singleListRoom();

    PagedResponse<PhongResponse1> searchRoomManager(int page, int size, Integer soLuongNguoi, String tenLoaiPhong, LocalDateTime checkIn, LocalDateTime checkOut) throws ServiceException;
    PagedResponse<PhongResponse1> searchRoomManager3(int page, int size, Integer soLuongNguoi, String tenLoaiPhong, LocalDateTime checkIn, LocalDateTime checkOut) throws ServiceException;

    PagedResponse<PhongResponse1> searchRoomManager2(int page, int size, Integer soLuongNguoi, String tenLoaiPhong);

    PagedResponse<PhongResponse1> searchRoomManager4(int page, int size, Integer soLuongNguoi, String tenLoaiPhong);

    PagedResponse<PhongResponse1> searchRoomByString(int page, int size, String searchInput);


    PagedResponse<PhongResponse1> searchRoomManagerByPrice(int page, int size, BigDecimal minGia, BigDecimal maxGia);

    PagedResponse<PhongResponse1> getListTopRoomOrder(int page, int size) throws ServiceException;

    PagedResponse<RoomMappingChiTietPhong> getListRoomOfFloar(int page, int size) throws ServiceException;
    PagedResponse<RoomMappingChiTietPhong> getListRoomActive(int page, int size, LocalDateTime checkIn, LocalDateTime checkOut) throws ServiceException;

    PagedResponse<PhongResponse1> getListRoomByTienIch(int page, int size, List<String> tienIch) throws ServiceException;
    PagedResponse<PhongResponse1> getListRoomByTienIch1(int page, int size, List<String> tienIch,int soLuongNguoi) throws ServiceException;
    PagedResponse<PhongResponse1> getListRoomByTienIch2(int page, int size, List<String> tienIch, String tenLoaiPhong) throws ServiceException;
    PagedResponse<PhongResponse1> getListRoomByTienIch3(int page, int size, List<String> tienIch, String tenLoaiPhong, int soLuongNguoi) throws ServiceException;

    PagedResponse<PhongResponse1> getListRoomByLoaiPhong(int page, int size, String tenLoaiPhong) throws ServiceException;

    PagedResponse<PhongResponse1> getListRoomByCheckDate(int page, int size, LocalDateTime checkIn, LocalDateTime checkOut) throws ServiceException;

    PagedResponse<PhongResponse1> getListRoomByCheckDateandLoaiPhong(int page, int size, String tenLoaiPhong, LocalDateTime checkIn, LocalDateTime checkOut) throws ServiceException;

    PagedResponse<PhongResponse1> getListRoomByCheckDateandTienIch(int page, int size, List<String> tienIch, LocalDateTime checkIn, LocalDateTime checkOut) throws ServiceException;

    PagedResponse<PhongResponse1> getListRoomByCheckDateandAll(int page, int size, List<String> tienIch, String tenLoaiPhong, LocalDateTime checkIn, LocalDateTime checkOut) throws ServiceException;
}

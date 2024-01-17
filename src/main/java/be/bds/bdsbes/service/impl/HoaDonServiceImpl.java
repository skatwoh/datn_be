package be.bds.bdsbes.service.impl;

import be.bds.bdsbes.domain.User;
import be.bds.bdsbes.entities.*;
import be.bds.bdsbes.exception.ServiceException;
import be.bds.bdsbes.payload.HoaDonResponse;
import be.bds.bdsbes.repository.*;
import be.bds.bdsbes.service.dto.HoaDonDTO;
import be.bds.bdsbes.service.iService.IHoaDonService;
import be.bds.bdsbes.service.mapper.HoaDonMapper;
import be.bds.bdsbes.utils.AppConstantsUtil;
import be.bds.bdsbes.utils.ServiceExceptionBuilderUtil;
import be.bds.bdsbes.utils.ValidationErrorUtil;
import be.bds.bdsbes.utils.dto.KeyValue;
import be.bds.bdsbes.utils.dto.PagedResponse;
import be.bds.bdsbes.utils.dto.ValidationErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@EnableScheduling
public class HoaDonServiceImpl implements IHoaDonService {

    @Autowired
    HoaDonRepository hoaDonRepository;

    @Autowired
    DatPhongRepository datPhongRepository;

    @Autowired
    KhachHangRepository khachHangRepository;

    @Autowired
    HoaDonMapper hoaDonMapper;

    @Autowired
    private ThongBaoRepository thongBaoRepository;

    @Scheduled(cron = "0 0 0 * * ?")
    public void expireStatus() {
        LocalDate date = LocalDate.now();
        List<HoaDon> expiredHoaDons = hoaDonRepository.findByStatus(2);
        List<HoaDon> expiredHoaDon = hoaDonRepository.findByExpiryDateBeforeAndStatus(date, 1);

        for (HoaDon hoaDon : expiredHoaDons) {
            ThongBao thongBao = new ThongBao();
            Long idUser = khachHangRepository.findByIdUser(hoaDon.getKhachHang().getId());
            thongBao.setUser(User.builder().id(idUser).build());
            thongBao.setNoiDung("Vui lòng thanh toán hóa đơn để xác nhận đặt phòng!");
            thongBao.setTimestamp(LocalDateTime.now());
            thongBaoRepository.save(thongBao);
        }

        for (HoaDon hoaDon : expiredHoaDon) {
            hoaDon.setTrangThai(3);
            ThongBao thongBao = new ThongBao();
            Long idUser = khachHangRepository.findByIdUser(hoaDon.getKhachHang().getId());
            thongBao.setUser(User.builder().id(idUser).build());
            thongBao.setNoiDung("Hóa đơn quá thời gian thanh toán, hệ thống tự động hủy hóa đơn!");
            thongBao.setTimestamp(LocalDateTime.now());
            thongBaoRepository.save(thongBao);
            hoaDonRepository.saveAll(expiredHoaDons);
        }

    }

    public int getNumberOfRecords() {
        Long count = hoaDonRepository.count();
        return count.intValue();
    }

    private String generateAutoCode() {
        int numberOfDigits = 4;

        int numberOfExistingRecords = getNumberOfRecords();

        String autoCode = "HD" + String.format("%0" + numberOfDigits + "d", numberOfExistingRecords + 1);

        return autoCode;
    }

    @Override
    public PagedResponse<HoaDonResponse> getHoaDon(int page, int size) throws ServiceException {
        if (page <= 0) {
            throw ServiceExceptionBuilderUtil.newBuilder()
                    .addError(new ValidationErrorResponse("page", ValidationErrorUtil.Invalid))
                    .build();
        }

        if (size > AppConstantsUtil.MAX_PAGE_SIZE) {
            List<KeyValue> params = new ArrayList<>();
            params.add(new KeyValue("max", String.valueOf(AppConstantsUtil.MAX_PAGE_SIZE)));

            throw ServiceExceptionBuilderUtil.newBuilder()
                    .addError(new ValidationErrorResponse("pageSize", ValidationErrorUtil.Invalid, params))
                    .build();
        }

        // Retrieve all entities
        Pageable pageable = PageRequest.of((page - 1), size, Sort.Direction.DESC, "id");
        Page<HoaDonResponse> entities = hoaDonRepository.getList(pageable);

        List<HoaDonResponse> dtos = entities.toList();
        return new PagedResponse<>(
                dtos,
                page,
                size,
                entities.getTotalElements(),
                entities.getTotalPages(),
                entities.isLast(),
                entities.getSort().toString()
        );
    }

    @Override
    public PagedResponse<HoaDonResponse> getHoaDonBySearch(int page, int size, String searchInput) throws ServiceException {
        // Retrieve all entities
        Pageable pageable = PageRequest.of((page - 1), size, Sort.Direction.DESC, "id");
        Page<HoaDonResponse> entities = hoaDonRepository.getListBySearch(pageable, searchInput);

        List<HoaDonResponse> dtos = entities.toList();
        return new PagedResponse<>(
                dtos,
                page,
                size,
                entities.getTotalElements(),
                entities.getTotalPages(),
                entities.isLast(),
                entities.getSort().toString()
        );
    }

    @Override
    public PagedResponse<HoaDonResponse> getHoaDonByCustomer(int page, int size, String hoTen, String sdt) throws ServiceException {
        if (page <= 0) {
            throw ServiceExceptionBuilderUtil.newBuilder()
                    .addError(new ValidationErrorResponse("page", ValidationErrorUtil.Invalid))
                    .build();
        }

        if (size > AppConstantsUtil.MAX_PAGE_SIZE) {
            List<KeyValue> params = new ArrayList<>();
            params.add(new KeyValue("max", String.valueOf(AppConstantsUtil.MAX_PAGE_SIZE)));

            throw ServiceExceptionBuilderUtil.newBuilder()
                    .addError(new ValidationErrorResponse("pageSize", ValidationErrorUtil.Invalid, params))
                    .build();
        }

        // Retrieve all entities
        Pageable pageable = PageRequest.of((page - 1), size, Sort.Direction.ASC, "id");
        Page<HoaDon> entities = hoaDonRepository.getListByCustumer(pageable, hoTen, sdt);

        List<HoaDonResponse> dtos = this.hoaDonMapper.toDtoList(entities.getContent());
        return new PagedResponse<>(
                dtos,
                page,
                size,
                entities.getTotalElements(),
                entities.getTotalPages(),
                entities.isLast(),
                entities.getSort().toString()
        );
    }

    /**
     * @param hoaDonDTO
     * @return
     * @throws ServiceException
     */
    @Override
    public Boolean create(HoaDonDTO hoaDonDTO) throws ServiceException {
        HoaDon hoaDon = new HoaDon();
        hoaDon.setMa(generateAutoCode());
        hoaDon.setNgayTao(LocalDateTime.now());
        hoaDon.setNgayThanhToan(hoaDonDTO.getNgayThanhToan());
        hoaDon.setTongTien(hoaDonDTO.getTongTien());
        hoaDon.setTrangThai(1);
        hoaDon.setGhiChu(hoaDonDTO.getGhiChu());
        Long idKhachHang = khachHangRepository.findByIdKhachHang(hoaDonDTO.getIdKhachHang());
        if (idKhachHang == null) {
            Long idKhachHang2 = khachHangRepository.findByI(hoaDonDTO.getIdKhachHang());
            hoaDon.setKhachHang(KhachHang.builder().id(idKhachHang2).build());
        } else {
            hoaDon.setKhachHang(KhachHang.builder().id(idKhachHang).build());
        }
        hoaDonRepository.save(hoaDon);
        return true;
    }

//    @Override
//    public Boolean update(HoaDonDTO hoaDonDTO) throws ServiceException {
//        Long id = hoaDonRepository.getId(hoaDonDTO.getIdKhachHang(), LocalDate.now());
//        HoaDon hoaDon = hoaDonRepository.findById(id).get();
//        BigDecimal tongTienCu = hoaDon.getTongTien();
//        BigDecimal tongTienMoi = hoaDonDTO.getTongTien().add(tongTienCu);
//        hoaDon.setTongTien(tongTienMoi);
//        hoaDonRepository.save(hoaDon);
//        return true;
//    }

    @Override
    public Boolean update(HoaDonDTO hoaDonDTO, Long id) throws ServiceException {
        Long idH = hoaDonRepository.getId(hoaDonDTO.getIdKhachHang(), LocalDate.now());
        HoaDon hoaDon = hoaDonRepository.findById(id).get();
        BigDecimal tongTienCu = hoaDon.getTongTien();
        BigDecimal tongTienMoi = hoaDonDTO.getTongTien().add(tongTienCu);
        hoaDon.setTongTien(tongTienMoi);
        hoaDonRepository.save(hoaDon);
        return true;
    }

    @Override
    public Boolean updateTienDichVu(BigDecimal tienDichVu, Long id) throws ServiceException {
        HoaDon hoaDon = hoaDonRepository.findById(id).get();
        BigDecimal tongTienCu = hoaDon.getTongTien();
        BigDecimal tongTienMoi = tienDichVu.add(tongTienCu);
        hoaDon.setTongTien(tongTienMoi);
        hoaDonRepository.save(hoaDon);
        return true;
    }

    @Override
    public Boolean createOrUpdate(HoaDonDTO hoaDonDTO) throws ServiceException {
        Long idKH = khachHangRepository.findByIdKhachHang(hoaDonDTO.getIdKhachHang());
        HoaDonResponse hoaDonResponse = hoaDonRepository.getHoaDon(idKH, LocalDate.now());
        if (hoaDonResponse != null && hoaDonResponse.getTrangThai() != 3) {
            this.update(hoaDonDTO, hoaDonResponse.getId());
            return true;
        }
        this.create(hoaDonDTO);
        return true;
    }

    @Override
    public Boolean updateTongTien(HoaDonDTO hoaDonDTO) {
        Long idKH = khachHangRepository.findByIdKhachHang(hoaDonDTO.getIdKhachHang());
        HoaDonResponse hoaDonResponse = hoaDonRepository.getHoaDon(hoaDonDTO.getIdKhachHang(), LocalDate.now());
        if (hoaDonResponse != null) {
            HoaDon hoaDon = hoaDonRepository.findById(hoaDonResponse.getId()).get();
            BigDecimal tongTienCu = hoaDon.getTongTien();
            BigDecimal tongTienMoi = tongTienCu.subtract(hoaDonDTO.getTongTien());
            hoaDon.setTongTien(tongTienMoi);
            hoaDonRepository.save(hoaDon);
            return true;
        }
        return false;
    }

    @Override
    public HoaDonResponse getOne(Long id) {
        return hoaDonRepository.get(id);
    }

    @Override
    public Integer updateTrangThai(Integer trangThai, Long id) throws ServiceException {
        HoaDon hoaDon = hoaDonRepository.findById(id).get();
        if (trangThai == 0) {
            if(hoaDon.getTrangThai() == 2){
                this.hoaDonRepository.updateTongTienById((hoaDon.getTongTien().multiply(new BigDecimal(95))).divide(new BigDecimal(100)), id);
            }
            hoaDon.setNgayThanhToan(LocalDateTime.now());
            this.hoaDonRepository.save(hoaDon);
            return hoaDonRepository.updateTrangThaiById(trangThai, id);
        }
        if (trangThai == 4) {
            for (DatPhong datPhong : datPhongRepository.findAll()) {
                if (datPhong.getHoaDon().getId() == hoaDon.getId()) {
                    datPhong.setTrangThai(0);
                    this.datPhongRepository.save(datPhong);
                }
            }
            return hoaDonRepository.updateTrangThaiById(trangThai, id);
        }
        if (trangThai == 2) {
            for (DatPhong datPhong : datPhongRepository.findAll()) {
                if (datPhong.getHoaDon().getId() == hoaDon.getId()) {
                    datPhong.setTrangThai(1);
                    this.datPhongRepository.save(datPhong);
                }
            }
            return hoaDonRepository.updateTrangThaiById(trangThai, id);
        }
        return hoaDonRepository.updateTrangThaiById(trangThai, id);
    }

    @Override
    public Boolean deleteHoaDon(HoaDonDTO hoaDonDTO) {
        Long idKH = khachHangRepository.findByIdKhachHang(hoaDonDTO.getIdKhachHang());
        HoaDonResponse hoaDonResponse = hoaDonRepository.getHoaDon(hoaDonDTO.getIdKhachHang(), LocalDate.now());
        System.out.println(hoaDonResponse.getId());
        List<DatPhong> list = datPhongRepository.getRoomByHoaDon0(hoaDonResponse.getId());
        if (list.size() == 0) {
            this.hoaDonRepository.delete(hoaDonRepository.findById(hoaDonResponse.getId()).get());
            return true;
        }
        return false;
    }

    public Boolean createTaiQuay(HoaDonDTO hoaDonDTO) throws ServiceException {
        HoaDon hoaDon = new HoaDon();
        hoaDon.setMa(generateAutoCode());
        hoaDon.setNgayTao(LocalDateTime.now());
        hoaDon.setNgayThanhToan(hoaDonDTO.getNgayThanhToan());
        hoaDon.setTongTien(hoaDonDTO.getTongTien());
        hoaDon.setTrangThai(3);
        hoaDon.setGhiChu(hoaDonDTO.getGhiChu());
//        Long idKhachHang = khachHangRepository.findByIdKhachHang(hoaDonDTO.getIdKhachHang());
//        if (idKhachHang == null) {
//            Long idKhachHang2 = khachHangRepository.findByI(hoaDonDTO.getIdKhachHang());
//            hoaDon.setKhachHang(KhachHang.builder().id(idKhachHang2).build());
//        } else {
//            hoaDon.setKhachHang(KhachHang.builder().id(idKhachHang).build());
//        }
        hoaDon.setKhachHang(KhachHang.builder().id(hoaDonDTO.getIdKhachHang()).build());
        hoaDonRepository.save(hoaDon);
        return true;
    }

    @Override
    public Boolean updateTaiQuay(HoaDonDTO hoaDonDTO, Long id) throws ServiceException {
        Long idH = hoaDonRepository.getId(hoaDonDTO.getIdKhachHang(), LocalDate.now());
        HoaDon hoaDon = hoaDonRepository.findById(id).get();
        BigDecimal tongTienCu = hoaDon.getTongTien();
        BigDecimal tongTienMoi = hoaDonDTO.getTongTien().add(tongTienCu);
        hoaDon.setTongTien(tongTienMoi);
        hoaDonRepository.save(hoaDon);
        return true;
    }

    @Override
    public Boolean createOrUpdateTaiQuay(HoaDonDTO hoaDonDTO) throws ServiceException {
        KhachHang khachHang = khachHangRepository.findById(hoaDonDTO.getIdKhachHang()).get();
        Long idKH = khachHangRepository.findByCccd(khachHang.getCccd());
        HoaDonResponse hoaDonResponse = hoaDonRepository.getHoaDonTaiQuay(idKH, LocalDate.now());
        if (hoaDonResponse != null && (hoaDonResponse.getTrangThai() == 3 || hoaDonResponse.getTrangThai() == 1)) {
            this.updateTaiQuay(hoaDonDTO, hoaDonResponse.getId());
            return true;
        }
        this.createTaiQuay(hoaDonDTO);
        return true;
    }

    @Override
    public BigDecimal getDoanhThuByDay(LocalDate checkIn, LocalDate checkOut) {
        return hoaDonRepository.getDoanhThuByDay(checkIn, checkOut);
    }

    @Override
    public BigDecimal getDoanhThuByToDay(int day, int month, int year) {
        return hoaDonRepository.getDoanhThuByToDay(day, month, year);
    }

    @Override
    public BigDecimal getDoanhThuByMonth(int month, int year) {
        return hoaDonRepository.getDoanhThuByMonth(month, year);
    }

    @Override
    public BigDecimal getDoanhThuByYear(int year) {
        return hoaDonRepository.getDoanhThuByYear(year);
    }

    @Override
    public BigDecimal getAllDoanhThu() {
        return hoaDonRepository.getAllDoanhThu();
    }

    @Override
    public Integer updateRankKhachHang(Long id, Long idTheThanhVien) {
//        BigDecimal tongTien = hoaDonRepository.getTongTienByKhachHang(id);
//        if(Integer.parseInt(tongTien.toString()) >=  100000000 ){
//            this.khachHangRepository.updateTheThanhVien(id , Long.parseLong("4"));
//             return true;
//        } else if(Integer.parseInt(tongTien.toString()) >= 60000000){
//            this.khachHangRepository.updateTheThanhVien(id , Long.parseLong("3"));
//            return true;
//        }else if(Integer.parseInt(tongTien.toString()) >= 20000000){
//            this.khachHangRepository.updateTheThanhVien(id , Long.parseLong("2"));
//            return true;
//        }
        this.khachHangRepository.updateTheThanhVien(id, idTheThanhVien);
        return 1;
    }

    @Override
    public BigDecimal getTongTienByKhachHang(Long id) {
        return hoaDonRepository.getTongTienByKhachHang(id);
    }

    @Override
    public PagedResponse<HoaDonResponse> findBillByCustomer(int page, int size, Long id) {
        Pageable pageable = PageRequest.of((page - 1), size, Sort.Direction.ASC, "id");
        Page<HoaDon> entities = hoaDonRepository.findByKhachHang(pageable, id);

        List<HoaDonResponse> dtos = this.hoaDonMapper.toDtoList(entities.getContent());
        return new PagedResponse<>(
                dtos,
                page,
                size,
                entities.getTotalElements(),
                entities.getTotalPages(),
                entities.isLast(),
                entities.getSort().toString()
        );
    }

    @Override
    public Integer updateGhiChubyId(String ghiChu, Long id) {
        this.hoaDonRepository.updateGhiChuById(ghiChu, id);
        return 1;
    }


}

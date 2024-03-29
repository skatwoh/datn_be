package be.bds.bdsbes.service.impl;

import be.bds.bdsbes.domain.User;
import be.bds.bdsbes.entities.*;
import be.bds.bdsbes.entities.enums.StatusRoom;
import be.bds.bdsbes.exception.ServiceException;
import be.bds.bdsbes.payload.PhongResponse1;
import be.bds.bdsbes.repository.*;
import be.bds.bdsbes.service.dto.KhachHangDTO;
import be.bds.bdsbes.service.iService.IDatPhongService;
import be.bds.bdsbes.service.dto.DatPhongDTO;
import be.bds.bdsbes.service.dto.response.DatPhongResponse;
import be.bds.bdsbes.service.mapper.DatPhongMapper;
import be.bds.bdsbes.service.mapper.PhongMapper;
import be.bds.bdsbes.utils.AppConstantsUtil;
import be.bds.bdsbes.utils.ServiceExceptionBuilderUtil;
import be.bds.bdsbes.utils.ValidationErrorUtil;
import be.bds.bdsbes.utils.dto.KeyValue;
import be.bds.bdsbes.utils.dto.PagedResponse;
import be.bds.bdsbes.utils.dto.ValidationErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class DatPhongServiceImpl implements IDatPhongService {
    @Autowired
    private PhongRepository phongRepository;
    @Autowired
    private KhachHangRepository khachHangRepository;
    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Autowired
    DatPhongRepository datPhongRepository;

    @Autowired
    ThongBaoRepository thongBaoRepository;

    @Autowired
    VoucherRepository voucherRepository;

    @Autowired
    DatPhongMapper datPhongMapper;

    @Autowired
    PhongMapper phongMapper;

    public int getNumberOfRecords() {
        Long count = datPhongRepository.count();
        return count.intValue();
    }


    private String generateAutoCode() {
        int numberOfDigits = 4;

        int numberOfExistingRecords = getNumberOfRecords();

        String autoCode = "DP" + String.format("%0" + numberOfDigits + "d", numberOfExistingRecords + 1);

        return autoCode;
    }

    @Override
    public List<DatPhong> getList() {
        return datPhongRepository.findAll();
    }

    @Override
    public List<DatPhongResponse> getAll() {
        return datPhongRepository.getAllDatPhong();
    }

    @Override
    public DatPhongResponse getOne(Long id) {
        return datPhongRepository.get(id);
    }

    /**
     * Creates a new `DatPhong` object based on the provided `DatPhongDTO`.
     *
     * @param datPhongDTO the `DatPhongDTO` object containing the data for creating the `DatPhong` object
     * @return the created `DatPhong` object
     */
    @Override
    public Boolean create(DatPhongDTO datPhongDTO) throws ServiceException {
        ZoneId timeZone = ZoneId.of("Asia/Ho_Chi_Minh");
        if (datPhongDTO.getCheckIn().isAfter(datPhongDTO.getCheckOut())) {
             throw ServiceExceptionBuilderUtil.newBuilder()
                    .addError(new ValidationErrorResponse("checkIn", ValidationErrorUtil.CheckIn))
                    .build();
        }
        if (datPhongDTO.getCheckIn().toLocalDate().isBefore(LocalDate.now())) {
            throw ServiceExceptionBuilderUtil.newBuilder()
                    .addError(new ValidationErrorResponse("checkIn", ValidationErrorUtil.CheckInBeforeDateNow))
                    .build();
        }

        if (datPhongRepository.validateCheckIn(datPhongDTO.getIdPhong(), datPhongDTO.getCheckIn(), datPhongDTO.getCheckOut())) {
            throw ServiceExceptionBuilderUtil.newBuilder()
                    .addError(new ValidationErrorResponse("checkIn", ValidationErrorUtil.CheckDateBook))
                    .build();
        }
        if (datPhongDTO.getCheckOut().toLocalDate().equals(datPhongDTO.getCheckIn().toLocalDate())) {
            throw ServiceExceptionBuilderUtil.newBuilder()
                    .addError(new ValidationErrorResponse("checkOut", ValidationErrorUtil.CheckInBeforeDateNow))
                    .build();
        }
        String autoGeneratedCode = generateAutoCode();
        ZonedDateTime checkInDate = datPhongDTO.getCheckIn().atZone(timeZone);
        ZonedDateTime adjustedCheckInDate = checkInDate.plusHours(7);
        ZonedDateTime checkOutDate = datPhongDTO.getCheckOut().atZone(timeZone);
        ZonedDateTime adjustedCheckOutDate = checkOutDate.plusHours(7);
        DatPhong datPhong = new DatPhong();
        datPhong.setMa(autoGeneratedCode);
        datPhong.setNgayDat(LocalDateTime.now());
        datPhong.setCheckIn(adjustedCheckInDate.toLocalDateTime());
        datPhong.setCheckOut(adjustedCheckOutDate.toLocalDateTime());
        datPhong.setSoNguoi(datPhongDTO.getSoNguoi());
        datPhong.setGhiChu(datPhongDTO.getGhiChu());
        datPhong.setTrangThai(datPhongDTO.getTrangThai());
        datPhong.setTongGia(datPhongDTO.getTongGia());
//        if(datPhongDTO.getIdVoucher() != null){
//            Voucher voucher = voucherRepository.findById(datPhongDTO.getIdVoucher()).get();
//            datPhong.setVoucher(Voucher.builder().id(datPhongDTO.getIdVoucher()).build());
//            voucher.setSoLuong(voucher.getSoLuong() - 1);
//            this.voucherRepository.save(voucher);
//        }
        Long idKhachHang = khachHangRepository.findByIdKhachHang(datPhongDTO.getUserId());
        datPhong.setKhachHang(KhachHang.builder().id(idKhachHang).build());
        datPhong.setPhong(Phong.builder().id(datPhongDTO.getIdPhong()).build());
        Long idHoaDon = hoaDonRepository.getId(idKhachHang, LocalDate.now());
        datPhong.setHoaDon(HoaDon.builder().id(idHoaDon).build());
        this.datPhongRepository.save(datPhong);
        return true;
    }

    @Override
    public DatPhong update(DatPhongDTO datPhongDTO, Long id) {
        Optional<DatPhong> optionalDatPhong = datPhongRepository.findById(id);
        if (optionalDatPhong.isPresent()) {
            DatPhong datPhong = optionalDatPhong.get();
            datPhong.setNgayDat(datPhongDTO.getNgayDat());
            datPhong.setCheckIn(datPhongDTO.getCheckIn());
            datPhong.setCheckOut(datPhongDTO.getCheckOut());
            datPhong.setSoNguoi(datPhongDTO.getSoNguoi());
            datPhong.setGhiChu(datPhongDTO.getGhiChu());
            datPhong.setTrangThai(datPhongDTO.getTrangThai());
//            datPhong.setVoucher(Voucher.builder().id(datPhongDTO.getIdVoucher()).build());
            datPhong.setKhachHang(KhachHang.builder().id(datPhongDTO.getUserId()).build());
            return datPhongRepository.save(datPhong);
        }
        return null;
    }

    /**
     * @param page
     * @param size
     * @return
     * @throws ServiceException
     */
    @Override
    public PagedResponse<DatPhongResponse> getRoomOrder(int page, int size) throws ServiceException {
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
        Page<DatPhong> entities = datPhongRepository.findAll(pageable);

        List<DatPhongResponse> dtos = this.datPhongMapper.toDtoList(entities.getContent());

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
    public PagedResponse<DatPhongResponse> getRoomOderByUser(int page, int size, Long id, Integer trangThai) throws ServiceException {
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
        Page<DatPhong> entities = datPhongRepository.getAllDatPhongByUser(pageable, id, trangThai);

        List<DatPhongResponse> dtos = this.datPhongMapper.toDtoList(entities.getContent());

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
    public PagedResponse<PhongResponse1> getPhongByUpperPrice(int page, int size, BigDecimal giaPhong, Long id, LocalDateTime checkIn, LocalDateTime checkOut) throws ServiceException {
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
        Page<Phong> entities = datPhongRepository.getPhongByUpperPrice(pageable, giaPhong, id, checkIn, checkOut);

        List<PhongResponse1> dtos = this.phongMapper.toDtoList(entities.getContent());

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
    public Integer updateTrangThai(Long id) throws ServiceException {

        DatPhong datPhong = datPhongRepository.findById(id).get();
        if (datPhong.getCheckIn().toLocalDate().isBefore(LocalDate.now()) || datPhong.getCheckIn().toLocalDate().equals(LocalDate.now())) {
            throw new ServiceException(ValidationErrorUtil.DeleteRoomOrder);
        }
        if (datPhong.getTrangThai() == 1 && datPhong.getCheckIn().toLocalDate().isAfter(LocalDate.now())) {
            this.datPhongRepository.updateTrangThaiById(0, id);
            HoaDon hoaDon = hoaDonRepository.findById(datPhong.getHoaDon().getId()).get();
            hoaDon.setTongTien(hoaDon.getTongTien().subtract(datPhong.getTongGia()));
            this.hoaDonRepository.save(hoaDon);
            return 1;
        }
        if (datPhong.getTrangThai() == 0) {
            return datPhongRepository.updateTrangThaiById(StatusRoom.STATUS1.getId(), id);
        }
        return null;
    }

    @Override
    public Integer updateStatus(Integer trangThai, Long id) throws ServiceException {
        return datPhongRepository.updateTrangThaiById(trangThai, id);
    }

    public Integer updateDatPhong(Long id, DatPhongDTO datPhongDTO) throws ServiceException {
        DatPhong datPhongCu = datPhongRepository.findById(id).get();
        if (datPhongDTO.getCheckIn().isAfter(datPhongDTO.getCheckOut())) {
            throw ServiceExceptionBuilderUtil.newBuilder()
                    .addError(new ValidationErrorResponse("checkIn", ValidationErrorUtil.CheckIn))
                    .build();
        }
        if (datPhongDTO.getCheckIn().toLocalDate().isBefore(LocalDate.now())) {
            throw ServiceExceptionBuilderUtil.newBuilder()
                    .addError(new ValidationErrorResponse("checkIn", ValidationErrorUtil.CheckInBeforeDateNow))
                    .build();
        }

        if (datPhongRepository.validateCheckIn(datPhongDTO.getIdPhong(), datPhongDTO.getCheckIn(), datPhongDTO.getCheckOut())) {
            throw ServiceExceptionBuilderUtil.newBuilder()
                    .addError(new ValidationErrorResponse("checkIn", ValidationErrorUtil.CheckDateBook))
                    .build();
        }
        this.datPhongRepository.updateDatPhongById(datPhongDTO.getIdPhong(), datPhongDTO.getTongGia(), id);
        HoaDon hoaDon = hoaDonRepository.findById(datPhongCu.getHoaDon().getId()).get();
        hoaDon.setTongTien(hoaDon.getTongTien().add(datPhongDTO.getTongGia()).subtract(datPhongCu.getTongGia()));
        this.hoaDonRepository.save(hoaDon);
        return 1;
    }

    @Override
    public PagedResponse<DatPhongResponse> getLichSuDatPhong(int page, int size, Long id) throws ServiceException {
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
        Page<DatPhong> entities = datPhongRepository.getLichSuDatPhong(pageable, id);

        List<DatPhongResponse> dtos = this.datPhongMapper.toDtoList(entities.getContent());

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
    public PagedResponse<DatPhongResponse> getRoomOfBill(int page, int size, Long userId) throws ServiceException {
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
        Long idKH = khachHangRepository.findByIdKhachHang(userId);
        Page<DatPhong> entities = datPhongRepository.getRoomByHoaDon(pageable, idKH);

        List<DatPhongResponse> dtos = this.datPhongMapper.toDtoList(entities.getContent());

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
    public PagedResponse<DatPhongResponse> getDatPhongByHoaDon(int page, int size, Long idHoaDon) throws ServiceException {
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
        Page<DatPhong> entities = datPhongRepository.getPageDatPhongByHoaDon(pageable, idHoaDon);

        List<DatPhongResponse> dtos = this.datPhongMapper.toDtoList(entities.getContent());

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
    public Boolean createListRoom(List<DatPhongDTO> datPhongDTOList) throws ServiceException {
        for (DatPhongDTO datPhongDTO : datPhongDTOList) {
            if (datPhongDTO.getCheckIn().isAfter(datPhongDTO.getCheckOut()) && datPhongDTO.getCheckOut().toLocalDate().isAfter(datPhongDTO.getCheckIn().toLocalDate()) == false) {
                throw ServiceExceptionBuilderUtil.newBuilder()
                        .addError(new ValidationErrorResponse("checkIn", ValidationErrorUtil.CheckIn))
                        .build();
            }
            if (datPhongDTO.getCheckIn().toLocalDate().isBefore(LocalDate.now())) {
                throw ServiceExceptionBuilderUtil.newBuilder()
                        .addError(new ValidationErrorResponse("checkIn", ValidationErrorUtil.CheckInBeforeDateNow))
                        .build();
            }
            if (datPhongRepository.validateCheckIn(datPhongDTO.getIdPhong(), datPhongDTO.getCheckIn(), datPhongDTO.getCheckOut())) {
                throw ServiceExceptionBuilderUtil.newBuilder()
                        .addError(new ValidationErrorResponse("checkIn", ValidationErrorUtil.CheckDateBook))
                        .build();
            }
//            if(datPhongDTO.getCheckIn().toLocalDate() == datPhongDTO.getCheckOut().toLocalDate()){
//                throw ServiceExceptionBuilderUtil.newBuilder()
//                        .addError(new ValidationErrorResponse("checkIn", ValidationErrorUtil.CheckDateBook))
//                        .build();
//            }

            String autoGeneratedCode = generateAutoCode();

            DatPhong datPhong = new DatPhong();
            datPhong.setMa(autoGeneratedCode);
            datPhong.setNgayDat(LocalDateTime.now());
            datPhong.setCheckIn(datPhongDTO.getCheckIn());
            datPhong.setCheckOut(datPhongDTO.getCheckOut());
            datPhong.setSoNguoi(datPhongDTO.getSoNguoi());
            datPhong.setGhiChu(datPhongDTO.getGhiChu());
            datPhong.setTrangThai(datPhongDTO.getTrangThai());
            datPhong.setTongGia(datPhongDTO.getTongGia());

            Long idKhachHang = khachHangRepository.findByIdKhachHang(datPhongDTO.getUserId());
            datPhong.setKhachHang(KhachHang.builder().id(idKhachHang).build());
            datPhong.setPhong(Phong.builder().id(datPhongDTO.getIdPhong()).build());

            Long idHoaDon = hoaDonRepository.getId(idKhachHang, LocalDate.now());
            datPhong.setHoaDon(HoaDon.builder().id(idHoaDon).build());

            this.datPhongRepository.save(datPhong);
        }

        return true;
    }

    @Override
    public Boolean datPhongTaiQuay(DatPhongDTO datPhongDTO) throws ServiceException {
        ZoneId timeZone = ZoneId.of("Asia/Ho_Chi_Minh");
        if (datPhongDTO.getCheckIn().isAfter(datPhongDTO.getCheckOut())) {
            throw ServiceExceptionBuilderUtil.newBuilder()
                    .addError(new ValidationErrorResponse("checkIn", ValidationErrorUtil.CheckIn))
                    .build();
        }
        if (datPhongDTO.getCheckIn().toLocalDate().isBefore(LocalDate.now())) {
            throw ServiceExceptionBuilderUtil.newBuilder()
                    .addError(new ValidationErrorResponse("checkIn", ValidationErrorUtil.CheckInBeforeDateNow))
                    .build();
        }

        if (datPhongRepository.validateCheckIn(datPhongDTO.getIdPhong(), datPhongDTO.getCheckIn(), datPhongDTO.getCheckOut())) {
            throw ServiceExceptionBuilderUtil.newBuilder()
                    .addError(new ValidationErrorResponse("checkIn", ValidationErrorUtil.CheckDateBook))
                    .build();
        }
        if (datPhongDTO.getCheckOut().toLocalDate().equals(datPhongDTO.getCheckIn().toLocalDate())) {
            throw ServiceExceptionBuilderUtil.newBuilder()
                    .addError(new ValidationErrorResponse("checkOut", ValidationErrorUtil.CheckInBeforeDateNow))
                    .build();
        }
        String autoGeneratedCode = generateAutoCode();
        ZonedDateTime checkInDate = datPhongDTO.getCheckIn().atZone(timeZone);
        ZonedDateTime adjustedCheckInDate = checkInDate.plusHours(7);
        ZonedDateTime checkOutDate = datPhongDTO.getCheckOut().atZone(timeZone);
        ZonedDateTime adjustedCheckOutDate = checkOutDate.plusHours(7);
        DatPhong datPhong = new DatPhong();
        datPhong.setMa(autoGeneratedCode);
        datPhong.setNgayDat(LocalDateTime.now());
        datPhong.setCheckIn(adjustedCheckInDate.toLocalDateTime());
        datPhong.setCheckOut(adjustedCheckOutDate.toLocalDateTime());
        datPhong.setSoNguoi(datPhongDTO.getSoNguoi());
        datPhong.setGhiChu(datPhongDTO.getGhiChu());
        datPhong.setTrangThai(datPhongDTO.getTrangThai());
        datPhong.setTongGia(datPhongDTO.getTongGia());
//        if(datPhongDTO.getIdVoucher() != null){
//            Voucher voucher = voucherRepository.findById(datPhongDTO.getIdVoucher()).get();
//            datPhong.setVoucher(Voucher.builder().id(datPhongDTO.getIdVoucher()).build());
//            voucher.setSoLuong(voucher.getSoLuong() - 1);
//            this.voucherRepository.save(voucher);
//        }
//        Long idKhachHang = khachHangRepository.findByIdKhachHang(datPhongDTO.getUserId());
        datPhong.setKhachHang(KhachHang.builder().id(datPhongDTO.getIdKhachHang()).build());
        datPhong.setPhong(Phong.builder().id(datPhongDTO.getIdPhong()).build());
        Long idHoaDon = hoaDonRepository.getIdTaiQuay(datPhongDTO.getIdKhachHang(), LocalDate.now());
        System.out.println(datPhongDTO.getIdKhachHang());
        datPhong.setHoaDon(HoaDon.builder().id(idHoaDon).build());
        this.datPhongRepository.save(datPhong);
        return true;
    }

    @Override
    public int getSoPhongDaDat(LocalDate CheckIn, LocalDate CheckOut) {
        return datPhongRepository.getSoPhongDaDat(CheckIn, CheckOut);
    }

    @Override
    public PagedResponse<DatPhongResponse> getDatPhongByKH(int page, int size, Long id) throws ServiceException {
        Pageable pageable = PageRequest.of((page - 1), size, Sort.Direction.ASC, "id");
        Page<DatPhong> entities = datPhongRepository.getPageDatPhongByKH(pageable, id);

        List<DatPhongResponse> dtos = this.datPhongMapper.toDtoList(entities.getContent());

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

}

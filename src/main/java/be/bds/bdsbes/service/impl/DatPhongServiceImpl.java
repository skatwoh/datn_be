package be.bds.bdsbes.service.impl;

import be.bds.bdsbes.domain.User;
import be.bds.bdsbes.entities.DatPhong;
import be.bds.bdsbes.entities.HoaDon;
import be.bds.bdsbes.entities.Phong;
import be.bds.bdsbes.entities.Voucher;
import be.bds.bdsbes.exception.ServiceException;
import be.bds.bdsbes.payload.PhongResponse1;
import be.bds.bdsbes.repository.*;
import be.bds.bdsbes.service.IDatPhongService;
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

        if (datPhongRepository.validateCheckIn(datPhongDTO.getIdPhong(), datPhongDTO.getCheckIn())) {
            throw ServiceExceptionBuilderUtil.newBuilder()
                    .addError(new ValidationErrorResponse("checkIn", ValidationErrorUtil.CheckDateBook))
                    .build();
        }
        String autoGeneratedCode = generateAutoCode();
        ZonedDateTime checkInDate = datPhongDTO.getCheckIn().atZone(timeZone);
        ZonedDateTime adjustedCheckInDate = checkInDate.minusHours(4);
        ZonedDateTime checkOutDate = datPhongDTO.getCheckOut().atZone(timeZone);
        ZonedDateTime adjustedCheckOutDate = checkOutDate.minusHours(4);
        DatPhong datPhong = new DatPhong();
        datPhong.setMa(autoGeneratedCode);
        datPhong.setNgayDat(LocalDateTime.now());
        datPhong.setCheckIn(adjustedCheckInDate.toLocalDateTime());
        datPhong.setCheckOut(adjustedCheckOutDate.toLocalDateTime());
        datPhong.setSoNguoi(datPhongDTO.getSoNguoi());
        datPhong.setGhiChu(datPhongDTO.getGhiChu());
        datPhong.setTrangThai(datPhongDTO.getTrangThai());
        datPhong.setTongGia(datPhongDTO.getTongGia());
        if(datPhongDTO.getIdVoucher() != null){
            datPhong.setVoucher(Voucher.builder().id(datPhongDTO.getIdVoucher()).build());
        }
        datPhong.setUser(User.builder().id(datPhongDTO.getUserId()).build());
        datPhong.setPhong(Phong.builder().id(datPhongDTO.getIdPhong()).build());
        Long idKH = khachHangRepository.findByIdKhachHang(datPhongDTO.getUserId());
        Long idHoaDon = hoaDonRepository.getId(idKH, LocalDate.now());
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
            datPhong.setVoucher(Voucher.builder().id(datPhongDTO.getIdVoucher()).build());
            datPhong.setUser(User.builder().id(datPhongDTO.getUserId()).build());
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
        Pageable pageable = PageRequest.of((page - 1), size, Sort.Direction.ASC, "id");
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
    public PagedResponse<PhongResponse1> getPhongByUpperPrice(int page, int size, BigDecimal giaPhong, Long id) throws ServiceException {
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
        Page<Phong> entities = datPhongRepository.getPhongByUpperPrice(pageable, giaPhong, id);

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
            return datPhongRepository.updateTrangThaiById(0, id);
        }
        if (datPhong.getTrangThai() == 0) {
            return datPhongRepository.updateTrangThaiById(1, id);
        }
        return null;
    }

    public Integer updateDatPhong(Long id, DatPhongDTO datPhongDTO) throws ServiceException {
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

        if (datPhongRepository.validateCheckIn(datPhongDTO.getIdPhong(), datPhongDTO.getCheckIn())) {
            throw ServiceExceptionBuilderUtil.newBuilder()
                    .addError(new ValidationErrorResponse("checkIn", ValidationErrorUtil.CheckDateBook))
                    .build();
        }
        return datPhongRepository.updateDatPhongById(datPhongDTO.getIdPhong(), datPhongDTO.getTongGia(), id);
    }
}

package be.bds.bdsbes.service.impl;

import be.bds.bdsbes.entities.HoaDon;
import be.bds.bdsbes.entities.KhachHang;
import be.bds.bdsbes.exception.ServiceException;
import be.bds.bdsbes.payload.HoaDonResponse;
import be.bds.bdsbes.repository.HoaDonRepository;
import be.bds.bdsbes.repository.KhachHangRepository;
import be.bds.bdsbes.service.IHoaDonService;
import be.bds.bdsbes.service.dto.HoaDonDTO;
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
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class HoaDonServiceImpl implements IHoaDonService {

    @Autowired
    HoaDonRepository hoaDonRepository;

    @Autowired
    KhachHangRepository khachHangRepository;

    @Autowired
    HoaDonMapper hoaDonMapper;

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
        Pageable pageable = PageRequest.of((page - 1), size, Sort.Direction.ASC, "id");
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
        HoaDon hoaDon =new HoaDon();
        hoaDon.setNgayTao(LocalDateTime.now());
        hoaDon.setNgayThanhToan(hoaDonDTO.getNgayThanhToan());
        hoaDon.setTongTien(hoaDonDTO.getTongTien());
        hoaDon.setTrangThai(1);
        hoaDon.setGhiChu(hoaDonDTO.getGhiChu());
        Long idKhachHang = khachHangRepository.findByIdKhachHang(hoaDonDTO.getIdKhachHang());
        hoaDon.setKhachHang(KhachHang.builder().id(idKhachHang).build());
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

    public Boolean createOrUpdate(HoaDonDTO hoaDonDTO) throws ServiceException {
        Long idKH = khachHangRepository.findByIdKhachHang(hoaDonDTO.getIdKhachHang());
        System.out.println(idKH);
        HoaDonResponse hoaDonResponse = hoaDonRepository.getHoaDon(idKH, LocalDate.now());
        if(hoaDonResponse != null){
            this.update(hoaDonDTO, hoaDonResponse.getId());
            return true;
        }
        this.create(hoaDonDTO);
        return true;
    }
}

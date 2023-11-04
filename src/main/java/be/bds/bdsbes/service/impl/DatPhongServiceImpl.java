package be.bds.bdsbes.service.impl;

import be.bds.bdsbes.domain.User;
import be.bds.bdsbes.entities.*;
import be.bds.bdsbes.exception.ServiceException;
import be.bds.bdsbes.payload.TaiKhoanResponse1;
import be.bds.bdsbes.repository.DatPhongRepository;
import be.bds.bdsbes.service.IDatPhongService;
import be.bds.bdsbes.service.dto.DatPhongDTO;
import be.bds.bdsbes.service.dto.response.DatPhongResponse;
import be.bds.bdsbes.service.mapper.DatPhongMapper;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class DatPhongServiceImpl implements IDatPhongService {

    @Autowired
    DatPhongRepository datPhongRepository;

    @Autowired
    DatPhongMapper datPhongMapper;

    @Override
    public List<DatPhong> getList() {
        return datPhongRepository.findAll();
    }

    @Override
    public List<DatPhongResponse> getAll() {
        return datPhongRepository.getAllDatPhong();
    }

    @Override
    public DatPhong getOne(Long id) {
        Optional<DatPhong> optionalDatPhong = datPhongRepository.findById(id);
        if(optionalDatPhong.isPresent()){
            DatPhong datPhong = optionalDatPhong.get();
            return datPhong;
        }
        return null;
    }

    /**
     * Creates a new `DatPhong` object based on the provided `DatPhongDTO`.
     *
     * @param  datPhongDTO  the `DatPhongDTO` object containing the data for creating the `DatPhong` object
     * @return              the created `DatPhong` object
     */
    @Override
    public DatPhong create(DatPhongDTO datPhongDTO) {
        DatPhong datPhong = new DatPhong();
        datPhong.setNgayDat(LocalDate.now());
        datPhong.setCheckIn(datPhongDTO.getCheckIn());
        datPhong.setCheckOut(datPhongDTO.getCheckOut());
        datPhong.setSoNguoi(datPhongDTO.getSoNguoi());
        datPhong.setGhiChu(datPhongDTO.getGhiChu());
        datPhong.setTrangThai(datPhongDTO.getTrangThai());
        datPhong.setVoucher(Voucher.builder().id(datPhongDTO.getIdVoucher()).build());
        datPhong.setUser(User.builder().id(datPhongDTO.getUserId()).build());
        datPhong.setPhong(Phong.builder().id(datPhongDTO.getIdPhong()).build());
        return datPhongRepository.save(datPhong);
    }

    @Override
    public DatPhong update(DatPhongDTO datPhongDTO, Long id) {
        Optional<DatPhong> optionalDatPhong = datPhongRepository.findById(id);
        if(optionalDatPhong.isPresent()){
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
}

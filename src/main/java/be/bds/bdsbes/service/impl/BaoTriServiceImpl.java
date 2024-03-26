package be.bds.bdsbes.service.impl;

import be.bds.bdsbes.entities.BaoTri;
import be.bds.bdsbes.exception.ServiceException;
import be.bds.bdsbes.payload.BaoTriResponse1;
import be.bds.bdsbes.payload.ChiTietPhongResponse1;
import be.bds.bdsbes.repository.BaoTriRepository;
import be.bds.bdsbes.service.iService.IBaoTriService;
import be.bds.bdsbes.service.dto.BaoTriDto;
import be.bds.bdsbes.service.mapper.BaoTriMapper;
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
@Service("baoTriServiceImpl")
public class BaoTriServiceImpl implements IBaoTriService {
    @Autowired
    BaoTriRepository baoTriRepository;
    @Autowired
    BaoTriMapper baoTriMapper;

    @Override
    public List<BaoTri> getList() {
        return baoTriRepository.findAll();
    }

    @Override
    public Page<BaoTri> getPage(Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return baoTriRepository.findAll(pageable);
    }

    @Override
    public BaoTriResponse1 getOne(Long id) {

        return baoTriRepository.get(id);
    }

    @Override
    public List<ChiTietPhongResponse1> getListCTP() {
        return baoTriRepository.singleListRoom();
    }

    @Override
    public BaoTri create(BaoTriDto baoTriDto) throws ServiceException {
        BaoTri baoTri = baoTriDto.dto(new BaoTri());
        if(baoTriDto.getNgayBatDau().isAfter(baoTriDto.getNgayKetThuc())){
            throw ServiceExceptionBuilderUtil.newBuilder()
                    .addError(new ValidationErrorResponse("ngayBatDau", ValidationErrorUtil.CheckIn))
                    .build();
        }
        if (baoTriDto.getNgayBatDau().isBefore(LocalDate.now())) {
            throw ServiceExceptionBuilderUtil.newBuilder()
                    .addError(new ValidationErrorResponse("ngayBatDau", ValidationErrorUtil.CheckInBeforeDateNow))
                    .build();
        }
        return baoTriRepository.save(baoTri);
    }

    @Override
    public PagedResponse<BaoTriResponse1> searchMaintenance(int page, int size, String searchInput)  throws ServiceException{
        Pageable pageable = PageRequest.of((page - 1), size, Sort.Direction.DESC, "id");
        Page<BaoTri> entities = baoTriRepository.searchMaintenance(pageable, searchInput);

        List<BaoTriResponse1> dtos = this.baoTriMapper.toDtoList(entities.getContent());
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
    public BaoTri update(BaoTriDto baoTriDto, Long id) {
        Optional<BaoTri> baoTriOptional = baoTriRepository.findById(id);
        if (baoTriOptional.isPresent()) {
            BaoTri baoTri = baoTriDto.dto(baoTriOptional.get());
            return baoTriRepository.save(baoTri);
        }
        return null;
    }

    @Override
    public Integer updateTrangThaiById( Long id) {
        BaoTri baoTri = baoTriRepository.findById(id).get();
        if (baoTri.getTrangThai() == 0) {
            return baoTriRepository.updateTrangThaiById(1, id);
        }
        if (baoTri.getTrangThai() == 1) {
            return baoTriRepository.updateTrangThaiById(0, id);
        }
        return null;
    }

    @Override
    public PagedResponse<BaoTriResponse1> getBaoTri(int page, int size) throws ServiceException {
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
        Page<BaoTri> entities = baoTriRepository.findAll(pageable);

        return new PagedResponse<>(
                this.baoTriMapper.toDtoList(entities.getContent()),
                page,
                size,
                entities.getTotalElements(),
                entities.getTotalPages(),
                entities.isLast(),
                entities.getSort().toString()
        );
    }

    @Override
    public List<BaoTriResponse1> getListByCTPhong(int page, int size, Long id, String ghiChu) throws ServiceException {
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
        Page<BaoTri> entities = baoTriRepository.getListByChiTiet(pageable, id, ghiChu);

        return new PagedResponse<>(
                this.baoTriMapper.toDtoList(entities.getContent()),
                page,
                size,
                entities.getTotalElements(),
                entities.getTotalPages(),
                entities.isLast(),
                entities.getSort().toString()
        ).getContent();
    }

}

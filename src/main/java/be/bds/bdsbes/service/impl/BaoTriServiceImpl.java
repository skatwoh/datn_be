package be.bds.bdsbes.service.impl;

import be.bds.bdsbes.entities.BaoTri;
import be.bds.bdsbes.entities.KhachHang;
import be.bds.bdsbes.entities.QuanLyDoiTac;
import be.bds.bdsbes.exception.ServiceException;
import be.bds.bdsbes.payload.BaoTriResponse1;
import be.bds.bdsbes.payload.KhachHangResponse1;
import be.bds.bdsbes.payload.QuanLyDoiTacResponse1;
import be.bds.bdsbes.repository.BaoTriRepository;
import be.bds.bdsbes.repository.QuanLyDoiTacRepository;
import be.bds.bdsbes.service.IBaoTriService;
import be.bds.bdsbes.service.dto.BaoTriDto;
import be.bds.bdsbes.service.dto.QuanLyDoiTacDTO;
import be.bds.bdsbes.service.mapper.BaoTriMapper;
import be.bds.bdsbes.service.mapper.QuanLyDoiTacMapper;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    public BaoTri getOne(Long id) {
        Optional<BaoTri> baoTriOptional = baoTriRepository.findById(id);
        if (baoTriOptional.isPresent()) {
            BaoTri baoTri = baoTriOptional.get();
            return baoTri;
        }
        return null;
    }

    @Override
    public BaoTri create(BaoTriDto baoTriDto) {
        BaoTri baoTri = baoTriDto.dto(new BaoTri());

        return baoTriRepository.save(baoTri);
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

}

package be.bds.bdsbes.service.impl;

import be.bds.bdsbes.entities.Phong;
import be.bds.bdsbes.exception.ServiceException;
import be.bds.bdsbes.payload.PhongResponse1;
import be.bds.bdsbes.repository.PhongRepository;
import be.bds.bdsbes.service.IPhongService;
import be.bds.bdsbes.service.dto.PhongDTO;
import be.bds.bdsbes.service.dto.response.PhongResponse;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PhongServiceImpl implements IPhongService {

    @Autowired
    PhongRepository phongRepository;
    @Autowired
    PhongMapper phongMapper;

    @Override
    public List<Phong> getList() {
        return phongRepository.findAll();
    }

    @Override
    public List<PhongResponse> getAllPhong() {
        return phongRepository.getAllPhong();
    }

    @Override
    public Page<Phong> getPage(Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return phongRepository.findAll(pageable);
    }

    @Override
    public Phong getOne(Long id) {
        Optional<Phong> phongOptional = phongRepository.findById(id);
        if(phongOptional.isPresent()){
            Phong phong = phongOptional.get();
            return phong;
        }
        return null;
    }

    @Override
    public Phong create(PhongDTO phongDTO) {
        Phong phong = phongDTO.dto(new Phong());
        return phongRepository.save(phong);
    }

    @Override
    public Phong update(PhongDTO phongDTO, Long id) {
        Optional<Phong> phongOptional = phongRepository.findById(id);
        if(phongOptional.isPresent()){
            Phong phong = phongDTO.dto(phongOptional.get());
            return phongRepository.save(phong);
        }
        return null;
    }

    @Override
    public PagedResponse<PhongResponse1> getPhong(int page, int size) throws ServiceException {
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
        Page<Phong> entities = phongRepository.findAll(pageable);

        return new PagedResponse<>(
                this.phongMapper.toDtoList(entities.getContent()),
                page,
                size,
                entities.getTotalElements(),
                entities.getTotalPages(),
                entities.isLast(),
                entities.getSort().toString()
        );
    }
}

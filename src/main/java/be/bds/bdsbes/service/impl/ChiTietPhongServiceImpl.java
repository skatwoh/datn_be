package be.bds.bdsbes.service.impl;

import be.bds.bdsbes.entities.ChiTietPhong;
import be.bds.bdsbes.entities.Phong;
import be.bds.bdsbes.exception.ServiceException;
import be.bds.bdsbes.payload.ChiTietPhongResponse1;
import be.bds.bdsbes.repository.ChiTietPhongRepository;
import be.bds.bdsbes.service.IChiTietPhongService;
import be.bds.bdsbes.service.dto.ChiTietPhongDTO;
import be.bds.bdsbes.service.mapper.ChiTietPhongMapper;
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
public class ChiTietPhongServiceImpl implements IChiTietPhongService {

    @Autowired
    private ChiTietPhongRepository chiTietPhongRepository;

    @Autowired
    private ChiTietPhongMapper chiTietPhongMapper;

    @Override
    public List<ChiTietPhong> getList() {
        return chiTietPhongRepository.findAll();
    }

    @Override
    public Page<ChiTietPhong> getPage(Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return chiTietPhongRepository.findAll(pageable);
    }

    @Override
    public ChiTietPhong getOne(Long id) {
        Optional<ChiTietPhong> optionalChiTietPhong = chiTietPhongRepository.findById(id);
        if(optionalChiTietPhong.isPresent()){
            ChiTietPhong chiTietPhong = optionalChiTietPhong.get();
            return chiTietPhong;
        }
        return null;
    }

    @Override
    public ChiTietPhong create(ChiTietPhongDTO chiTietPhongDTO) {
        ChiTietPhong chiTietPhong = chiTietPhongDTO.dto(new ChiTietPhong());
        return chiTietPhongRepository.save(chiTietPhong);
    }

    @Override
    public ChiTietPhong update(ChiTietPhongDTO chiTietPhongDTO, Long id) {
        Optional<ChiTietPhong> optionalChiTietPhong = chiTietPhongRepository.findById(id);
        if(optionalChiTietPhong.isPresent()){
            ChiTietPhong chiTietPhong = chiTietPhongDTO.dto(optionalChiTietPhong.get());
            return chiTietPhongRepository.save(chiTietPhong);
        }
        return null;
    }

    @Override
    public PagedResponse<ChiTietPhongResponse1> getChiTietPhong(int page, int size) throws ServiceException {
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
        Page<ChiTietPhong> entities = chiTietPhongRepository.findAll(pageable);

        return new PagedResponse<>(
                this.chiTietPhongMapper.toDtoList(entities.getContent()),
                page,
                size,
                entities.getTotalElements(),
                entities.getTotalPages(),
                entities.isLast(),
                entities.getSort().toString()
        );
    }
}

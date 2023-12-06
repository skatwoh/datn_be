package be.bds.bdsbes.service.impl;

import be.bds.bdsbes.entities.TaiSan;
import be.bds.bdsbes.exception.ServiceException;
import be.bds.bdsbes.payload.TaiSanResponse1;
import be.bds.bdsbes.repository.TaiSanRepository;
import be.bds.bdsbes.service.iService.ITaiSanService;
import be.bds.bdsbes.service.dto.TaiSanDTO;
import be.bds.bdsbes.service.dto.response.TaiSanResponse;
import be.bds.bdsbes.service.mapper.TaiSanMapper;
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
@Service("taiSanServiceImpl")
public class TaiSanServiceImpl implements ITaiSanService {
    @Autowired
    private TaiSanRepository taiSanRepository;
    @Autowired
    private TaiSanMapper taiSanMapper;

    @Override
    public List<TaiSanResponse> getList() {
        return taiSanRepository.getAllTaiSan();
    }
    @Override
    public TaiSanResponse1 getTaiSan(Long id) {
        return taiSanRepository.getTaiSan(id);
    }

    @Override
    public Page<TaiSan> getPage(Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return taiSanRepository.findAll(pageable);
    }


    @Override
    public TaiSan create(TaiSanDTO taiSanDTO) {
        TaiSan taiSan = taiSanDTO.dto(new TaiSan());
        return taiSanRepository.save(taiSan);
    }

    @Override
    public TaiSan update(TaiSanDTO taiSanDTO, Long id) {
        Optional<TaiSan> taiSanOptional = taiSanRepository.findById(id);
        if(taiSanOptional.isPresent()){
            TaiSan taiSan = taiSanDTO.dto(taiSanOptional.get());
            return taiSanRepository.save(taiSan);
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
    public PagedResponse<TaiSanResponse1> getAccounts(int page, int size) throws ServiceException {
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
        Page<TaiSan> entities = taiSanRepository.findAll(pageable);

        return new PagedResponse<>(
                this.taiSanMapper.toDtoList(entities.getContent()),
                page,
                size,
                entities.getTotalElements(),
                entities.getTotalPages(),
                entities.isLast(),
                entities.getSort().toString()
        );
    }
}

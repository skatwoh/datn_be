package be.bds.bdsbes.service.impl;

import be.bds.bdsbes.domain.User;
import be.bds.bdsbes.entities.ThongBao;
import be.bds.bdsbes.exception.ServiceException;
import be.bds.bdsbes.repository.ThongBaoRepository;
import be.bds.bdsbes.service.iService.IThongBaoService;
import be.bds.bdsbes.service.dto.ThongBaoDTO;
import be.bds.bdsbes.service.dto.response.ThongBaoResponse;
import be.bds.bdsbes.service.mapper.ThongBaoMapper;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service("thongBaoService")
public class ThongBaoServiceImpl implements IThongBaoService {

    @Autowired
    ThongBaoRepository thongBaoRepository;

    @Autowired
    ThongBaoMapper thongBaoMapper;


    /**
     * @param userId
     * @return
     */
    @Override
    public List<ThongBaoResponse> getAllThongBao(Long userId) {
        return thongBaoRepository.getAllThongBao(userId);
    }

    /**
     * @param page
     * @param size
     * @param userId
     * @return
     * @throws ServiceException
     */
    @Override
    public PagedResponse<ThongBaoResponse> listThongBao(int page, int size, Long userId) throws ServiceException {
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
        Page<ThongBao> entities = thongBaoRepository.listAll(pageable, userId);

        List<ThongBaoResponse> dtos = this.thongBaoMapper.toDtoList(entities.getContent());
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
     * @param thongBaoDTO
     * @return
     * @throws ServiceException
     */
    @Override
    public Boolean create(ThongBaoDTO thongBaoDTO){

        ThongBao thongBao = new ThongBao();
        thongBao.setMaDatPhong(thongBaoDTO.getMaDatPhong());
        thongBao.setNoiDung(thongBaoDTO.getNoiDung());
        thongBao.setTrangThai(thongBaoDTO.getTrangThai());
        thongBao.setTimestamp(LocalDateTime.now());
        thongBao.setUser(User.builder().id(thongBaoDTO.getUserId()).build());
        thongBaoRepository.save(thongBao);
        return true;
    }

}

package be.bds.bdsbes.service.impl;

import be.bds.bdsbes.entities.CoSoVatChat;
import be.bds.bdsbes.exception.ServiceException;
import be.bds.bdsbes.payload.CoSoVatChatResponse1;
import be.bds.bdsbes.repository.CoSoVatChatRepository;
import be.bds.bdsbes.service.iService.ICoSoVatChatService;
import be.bds.bdsbes.service.dto.CoSoVatChatDTO;
import be.bds.bdsbes.service.mapper.CoSoVatChatMapper;
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
@Service("CoSoVatChatServiceImpl")
public class CoSoVatChatServiceImpl implements ICoSoVatChatService {
    @Autowired
    CoSoVatChatRepository coSoVatChatRepository;
    @Autowired
    private CoSoVatChatMapper coSoVatChatMapper;

    @Override
    public List<CoSoVatChat> getList() {
        return coSoVatChatRepository.findAll();
    }

    @Override
    public Page<CoSoVatChat> getPage(Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return coSoVatChatRepository.findAll(pageable);
    }

    @Override
    public CoSoVatChat getOne(Long id) {
        Optional<CoSoVatChat> coSoVatChatOptional = coSoVatChatRepository.findById(id);
        if(coSoVatChatOptional.isPresent()){
           CoSoVatChat coSoVatChat = coSoVatChatOptional.get();
            return coSoVatChat;
        }
        return null;
    }

    @Override
    public CoSoVatChat create(CoSoVatChatDTO coSoVatChatDTO) {
        CoSoVatChat coSoVatChat = coSoVatChatDTO.dto(new CoSoVatChat());
        return coSoVatChatRepository.save(coSoVatChat);
    }

    @Override
    public CoSoVatChat update(CoSoVatChatDTO coSoVatChatDTO, Long id) {
        Optional<CoSoVatChat> coSoVatChatOptional = coSoVatChatRepository.findById(id);
        if(coSoVatChatOptional.isPresent()){
            CoSoVatChat coSoVatChat = coSoVatChatDTO.dto(coSoVatChatOptional.get());
            return coSoVatChatRepository.save(coSoVatChat);
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
    public PagedResponse<CoSoVatChatResponse1> getAccounts(int page, int size) throws ServiceException {
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
        Page<CoSoVatChat> entities = coSoVatChatRepository.findAll(pageable);

        return new PagedResponse<>(
                this.coSoVatChatMapper.toDtoList(entities.getContent()),
                page,
                size,
                entities.getTotalElements(),
                entities.getTotalPages(),
                entities.isLast(),
                entities.getSort().toString()
        );
    }
}

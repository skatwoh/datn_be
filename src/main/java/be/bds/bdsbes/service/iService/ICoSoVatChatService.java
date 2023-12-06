package be.bds.bdsbes.service.iService;

import be.bds.bdsbes.entities.CoSoVatChat;
import be.bds.bdsbes.exception.ServiceException;
import be.bds.bdsbes.payload.CoSoVatChatResponse1;
import be.bds.bdsbes.service.dto.CoSoVatChatDTO;
import be.bds.bdsbes.utils.dto.PagedResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ICoSoVatChatService {
    List<CoSoVatChat> getList();

    Page<CoSoVatChat> getPage(Integer page);

    CoSoVatChat getOne(Long id);

    CoSoVatChat create(CoSoVatChatDTO coSoVatChatDTO);

    CoSoVatChat update(CoSoVatChatDTO coSoVatChatDTO, Long id);

    PagedResponse<CoSoVatChatResponse1> getAccounts(int page, int size) throws ServiceException;
}

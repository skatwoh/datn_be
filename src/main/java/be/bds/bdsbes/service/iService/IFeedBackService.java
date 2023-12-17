package be.bds.bdsbes.service.iService;

import be.bds.bdsbes.exception.ServiceException;
import be.bds.bdsbes.service.dto.FeedbackDTO;
import be.bds.bdsbes.service.dto.response.FeedbackResponse;
import be.bds.bdsbes.utils.dto.PagedResponse;

public interface IFeedBackService {

    Boolean create(FeedbackDTO feedbackDTO);

    PagedResponse<FeedbackResponse> listFeedback(int page, int size, Long idChiTietPhong) throws ServiceException;

    Long count();
}

package be.bds.bdsbes.resource;

import be.bds.bdsbes.exception.ServiceException;
import be.bds.bdsbes.service.dto.FeedbackDTO;
import be.bds.bdsbes.service.iService.IFeedBackService;
import be.bds.bdsbes.utils.AppConstantsUtil;
import be.bds.bdsbes.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rpc/bds/feed-back")
public class FeedbackController {

    @Autowired
    IFeedBackService iFeedBackService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody FeedbackDTO feedbackDTO) {
       return ResponseUtil.wrap(this.iFeedBackService.create(feedbackDTO));
    }

    @GetMapping("/list")
    public ResponseEntity<?> getList(
            @RequestParam(value = "page", defaultValue = AppConstantsUtil.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstantsUtil.DEFAULT_PAGE_SIZE) int size,
            @RequestParam(value = "idChiTietPhong", defaultValue = "") Long idChiTietPhong) {
        try {
            return ResponseUtil.wrap(this.iFeedBackService.listFeedback(page, size, idChiTietPhong));
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/count")
    public Long count(){
        return this.iFeedBackService.count();
    }
}

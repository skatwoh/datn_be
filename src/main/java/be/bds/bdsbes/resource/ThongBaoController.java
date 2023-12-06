package be.bds.bdsbes.resource;

import be.bds.bdsbes.exception.ServiceException;
import be.bds.bdsbes.service.iService.IThongBaoService;
import be.bds.bdsbes.service.dto.ThongBaoDTO;
import be.bds.bdsbes.utils.AppConstantsUtil;
import be.bds.bdsbes.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/rpc/bds/thong-bao")
public class ThongBaoController {

    @Autowired
    private IThongBaoService ithongBaoService;

    @GetMapping("/list")
    public ResponseEntity<?> getAll(@RequestParam(value = "userId") Long userId) {
        return ResponseEntity.ok(ithongBaoService.getAllThongBao(userId));
    }

    @GetMapping("list-notification")
    public ResponseEntity<?> getList(
            @RequestParam(value = "page", defaultValue = AppConstantsUtil.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstantsUtil.DEFAULT_PAGE_SIZE) int size,
            @RequestParam(value = "userId", defaultValue = "") Long userId) {
        try {
            return ResponseUtil.wrap(this.ithongBaoService.listThongBao(page, size, userId));
        } catch (Exception ex) {
            log.error(this.getClass().getName(), ex);
            return ResponseUtil.generateErrorResponse(ex);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("send-notification")
    public ResponseEntity<?> sendNotification(@Valid @RequestBody ThongBaoDTO thongBaoDTO) {
        Boolean response = ithongBaoService.create(thongBaoDTO);
        return ResponseEntity.ok(response);
    }
}

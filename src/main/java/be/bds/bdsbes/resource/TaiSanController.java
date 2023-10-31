package be.bds.bdsbes.resource;

import be.bds.bdsbes.exception.ServiceException;
import be.bds.bdsbes.service.ITaiSanService;
import be.bds.bdsbes.service.dto.TaiSanDTO;
import be.bds.bdsbes.service.impl.TaiSanServiceImpl;
import be.bds.bdsbes.utils.AppConstantsUtil;
import be.bds.bdsbes.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
@Slf4j
@RestController
@RequestMapping("/api/tai-san")
public class TaiSanController {
    @Autowired
    private ITaiSanService ITaiSanService = new TaiSanServiceImpl();

    @GetMapping("/list")
    public ResponseEntity<?> getList(
            @RequestParam(value = "page", defaultValue = AppConstantsUtil.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstantsUtil.DEFAULT_PAGE_SIZE) int size) {
        try {
            return ResponseUtil.wrap(this.ITaiSanService.getAccounts(page, size));
        } catch (Exception ex) {
            log.error(this.getClass().getName(), ex);
            return ResponseUtil.generateErrorResponse(ex);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("get-page")
    public ResponseEntity<?> getPage(@RequestParam(defaultValue = "0", name = "page") Integer page){
        return ResponseEntity.ok(ITaiSanService.getPage(page));
    }

    @GetMapping("detail")
    public ResponseEntity<?> getTaiSan(@RequestParam( value = "id") Long id){
        if(ITaiSanService.getTaiSan(id) == null){
            return ResponseEntity.ok("Not found");
        }
        return ResponseEntity.ok(ITaiSanService.getTaiSan(id));
    }

    @PostMapping("create")
    public ResponseEntity<?> add(@RequestBody @Valid TaiSanDTO taiSanDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            List<ObjectError> errorList = bindingResult.getAllErrors();
            return ResponseEntity.ok(errorList);
        }
        return ResponseEntity.ok(ITaiSanService.create(taiSanDTO));
    }

    @PutMapping("update")
    public ResponseEntity<?> update(@RequestParam(value = "id") Long id, @RequestBody @Valid TaiSanDTO taiSanDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            List<ObjectError> errorList = bindingResult.getAllErrors();
            return ResponseEntity.ok(errorList);
        }
        if(ITaiSanService.update(taiSanDTO, id) == null){
            return ResponseEntity.ok("Update failed");
        }
        return ResponseEntity.ok(ITaiSanService.update(taiSanDTO, id));
    }
}

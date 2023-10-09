package be.bds.bdsbes.resource;

import be.bds.bdsbes.exception.ServiceException;
import be.bds.bdsbes.service.IDichVuService;
import be.bds.bdsbes.service.dto.DichVuDTO;
import be.bds.bdsbes.service.impl.DichVuServiceImpl;
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
@RequestMapping("/rpc/bds/dich-vu")
public class DichVuController {
    @Autowired
    private IDichVuService IDichVuService = new DichVuServiceImpl();

    @GetMapping("/list")
    public ResponseEntity<?> getList(
            @RequestParam(value = "page", defaultValue = AppConstantsUtil.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstantsUtil.DEFAULT_PAGE_SIZE) int size) {
        try {
            return ResponseUtil.wrap(this.IDichVuService.getAccounts(page, size));
        } catch (Exception ex) {
            log.error(this.getClass().getName(), ex);
            return ResponseUtil.generateErrorResponse(ex);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }


    @GetMapping("detail/{id}")
    public ResponseEntity<?> getOne(@PathVariable("id") Long id){
        if(IDichVuService.getOne(id) == null){
            return ResponseEntity.badRequest().body("Không tìm thấy");
        }
        return ResponseEntity.ok(IDichVuService.getOne(id));
    }

    @PostMapping("create")
    public ResponseEntity<?> create(@RequestBody @Valid DichVuDTO dichVuDTO, BindingResult result){
        if(result.hasErrors()){
            List<ObjectError> errorList = result.getAllErrors();
            return ResponseEntity.badRequest().body(errorList);
        }
        return ResponseEntity.ok(IDichVuService.create(dichVuDTO));
    }

    @PutMapping("update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody @Valid DichVuDTO dichVuDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            List<ObjectError> errorList = bindingResult.getAllErrors();
            return ResponseEntity.ok(errorList);
        }
        if(IDichVuService.update(dichVuDTO, id) == null){
            return ResponseEntity.ok("Update failed");
        }
        return ResponseEntity.ok(IDichVuService.update(dichVuDTO, id));
    }
}
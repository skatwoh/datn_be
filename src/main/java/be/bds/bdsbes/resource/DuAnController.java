package be.bds.bdsbes.resource;

import be.bds.bdsbes.exception.ServiceException;
import be.bds.bdsbes.service.iService.IDuAnService;
import be.bds.bdsbes.service.dto.DuAnDTO;
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
@RequestMapping("/rpc/bds/du-an")
public class DuAnController {
    @Autowired
    IDuAnService iDuAnService;

    @GetMapping("/list")
    public ResponseEntity<?> getList(
            @RequestParam(value = "page", defaultValue = AppConstantsUtil.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstantsUtil.DEFAULT_PAGE_SIZE) int size) {
        try {
            return ResponseUtil.wrap(this.iDuAnService.getAccounts(page, size));
        } catch (Exception ex) {
            log.error(this.getClass().getName(), ex);
            return ResponseUtil.generateErrorResponse(ex);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("detail")
    public ResponseEntity<?> getOne(@RequestParam(value = "id") Long id){
        return ResponseEntity.ok(iDuAnService.getDuAn(id));
    }

    @PostMapping("create")
    public ResponseEntity<?> create(@RequestBody @Valid DuAnDTO duAnDTO, BindingResult result){
        if(result.hasErrors()){
            List<ObjectError> errorList = result.getAllErrors();
            return ResponseEntity.badRequest().body(errorList);
        }
        return ResponseEntity.ok(iDuAnService.create(duAnDTO));
    }

    @PutMapping("update")
    public ResponseEntity<?> update(@RequestParam (value = "id") Long id, @RequestBody @Valid DuAnDTO duAnDTO, BindingResult result){
        if(result.hasErrors()){
            List<ObjectError> errorList = result.getAllErrors();
            return ResponseEntity.badRequest().body(errorList);
        }
        return ResponseEntity.ok(iDuAnService.update(duAnDTO, id));
    }
    @PutMapping("delete")
    public ResponseEntity<?> delete(@RequestParam(value = "id") Long id) {
        return ResponseEntity.ok(this.iDuAnService.updateTrangThai(id));
    }
    @GetMapping("search")
    public ResponseEntity<?> getListbySearch(
            @RequestParam(value = "page", defaultValue = AppConstantsUtil.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstantsUtil.DEFAULT_PAGE_SIZE) int size,
            @RequestParam(value = "input", defaultValue = "") String searchInput) {
        try {
            return ResponseUtil.wrap(this.iDuAnService.searchProjetc(page, size, searchInput));
        } catch (Exception ex) {
            log.error(this.getClass().getName(), ex);
            return ResponseUtil.generateErrorResponse(ex);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }
}

package be.bds.bdsbes.resource;

import be.bds.bdsbes.exception.ServiceException;
import be.bds.bdsbes.service.iService.IQuanLyDoiTacService;
import be.bds.bdsbes.service.dto.QuanLyDoiTacDTO;
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
@RequestMapping("/rpc/bds/quan-ly-doi-tac")
public class QuanLyDoiTacController {
    @Autowired
    IQuanLyDoiTacService quanLyDoiTacService;

    @GetMapping("list")
    public ResponseEntity<?> getList(
            @RequestParam(value = "page", defaultValue = AppConstantsUtil.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstantsUtil.DEFAULT_PAGE_SIZE) int size) {
        try {
            return ResponseUtil.wrap(this.quanLyDoiTacService.getQuanLyDoiTac(page, size));
        } catch (Exception ex) {
            log.error(this.getClass().getName(), ex);
            return ResponseUtil.generateErrorResponse(ex);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("detail")
    public ResponseEntity<?> getOne(@RequestParam(value = "id") Long id){

        return ResponseEntity.ok(quanLyDoiTacService.getOne(id));
    }

    @PostMapping("create")
    public ResponseEntity<?> create(@RequestBody @Valid QuanLyDoiTacDTO quanLyDoiTacDTO, BindingResult result){
        if(result.hasErrors()){
            List<ObjectError> errorList = result.getAllErrors();
            return ResponseEntity.badRequest().body(errorList);
        }
        return ResponseEntity.ok(quanLyDoiTacService.create(quanLyDoiTacDTO));
    }

    @PutMapping("update")
    public ResponseEntity<?> update(@RequestParam(value = "id") Long id, @RequestBody @Valid QuanLyDoiTacDTO quanLyDoiTacDTO, BindingResult result){
        if(result.hasErrors()){
            List<ObjectError> errorList = result.getAllErrors();
            return ResponseEntity.badRequest().body(errorList);
        }
        return ResponseEntity.ok(quanLyDoiTacService.update(quanLyDoiTacDTO, id));
    }
    @PutMapping("delete")
    public ResponseEntity<?> delete(@RequestParam(value = "id") Long id) {
        return ResponseEntity.ok(this.quanLyDoiTacService.updateTrangThaiById(id));
    }
    @GetMapping("search")
    public ResponseEntity<?> getListbySearch(
            @RequestParam(value = "page", defaultValue = AppConstantsUtil.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstantsUtil.DEFAULT_PAGE_SIZE) int size,
            @RequestParam(value = "input", defaultValue = "") String searchInput) {
        try {
            return ResponseUtil.wrap(this.quanLyDoiTacService.searchPartner(page, size, searchInput));
        } catch (Exception ex) {
            log.error(this.getClass().getName(), ex);
            return ResponseUtil.generateErrorResponse(ex);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }

}

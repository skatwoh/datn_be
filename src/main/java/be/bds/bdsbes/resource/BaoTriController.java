package be.bds.bdsbes.resource;

import be.bds.bdsbes.entities.BaoTri;
import be.bds.bdsbes.exception.ServiceException;
import be.bds.bdsbes.service.iService.IBaoTriService;
import be.bds.bdsbes.service.dto.BaoTriDto;
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
@RequestMapping("/rpc/bds/bao-tri")
public class BaoTriController {
    @Autowired
    IBaoTriService baoTriService;

    @GetMapping("list")
    public ResponseEntity<?> getList(
            @RequestParam(value = "page", defaultValue = AppConstantsUtil.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstantsUtil.DEFAULT_PAGE_SIZE) int size) {
        try {
            return ResponseUtil.wrap(this.baoTriService.getBaoTri(page, size));
        } catch (Exception ex) {
            log.error(this.getClass().getName(), ex);
            return ResponseUtil.generateErrorResponse(ex);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("detail")
    public ResponseEntity<?> getOne(@RequestParam(value = "id") Long id) {

        return ResponseEntity.ok(baoTriService.getOne(id));
    }
    @GetMapping("list-room-infor")
    public ResponseEntity<?> getSingleListRoomInfor() {

        return ResponseEntity.ok(baoTriService.getListCTP());
    }
    @PostMapping("create")
    public ResponseEntity<?> create(@RequestBody @Valid BaoTriDto baoTriDto, BindingResult result) throws ServiceException {
        try {
            BaoTri response = baoTriService.create(baoTriDto);
            return ResponseUtil.wrap(response);
        } catch (ServiceException e) {
            log.error(this.getClass().getName(), e);
            return ResponseUtil.generateErrorResponse(e);
        }
    }

    @PutMapping("update")
    public ResponseEntity<?> update(@RequestParam(value = "id") Long id, @RequestBody @Valid BaoTriDto baoTriDto, BindingResult result) {
        if (result.hasErrors()) {
            List<ObjectError> errorList = result.getAllErrors();
            return ResponseEntity.badRequest().body(errorList);
        }
        return ResponseEntity.ok(baoTriService.update(baoTriDto, id));
    }
    @PutMapping("delete")
    public ResponseEntity<?> delete(@RequestParam(value = "id") Long id) {
        return ResponseEntity.ok(this.baoTriService.updateTrangThaiById(id));
    }
    @GetMapping("search")
    public ResponseEntity<?> getListbySearch(
            @RequestParam(value = "page", defaultValue = AppConstantsUtil.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstantsUtil.DEFAULT_PAGE_SIZE) int size,
            @RequestParam(value = "input", defaultValue = "") String searchInput) {
        try {
            return ResponseUtil.wrap(this.baoTriService.searchMaintenance(page, size, searchInput));
        } catch (Exception ex) {
            log.error(this.getClass().getName(), ex);
            return ResponseUtil.generateErrorResponse(ex);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("list-by-ct")
    public ResponseEntity<?> getListbyCTPhong(
            @RequestParam(value = "page", defaultValue = AppConstantsUtil.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstantsUtil.DEFAULT_PAGE_SIZE) int size,
            @RequestParam(value = "id") Long id, @RequestParam(value = "ghiChu") String ghiChu) {
        try {
            return ResponseUtil.wrap(this.baoTriService.getListByCTPhong(page, size, id, ghiChu));
        } catch (Exception ex) {
            log.error(this.getClass().getName(), ex);
            return ResponseUtil.generateErrorResponse(ex);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }
}

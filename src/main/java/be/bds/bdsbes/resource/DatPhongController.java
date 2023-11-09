package be.bds.bdsbes.resource;

import be.bds.bdsbes.exception.ServiceException;
import be.bds.bdsbes.service.IDatPhongService;
import be.bds.bdsbes.service.dto.DatPhongDTO;
import be.bds.bdsbes.utils.AppConstantsUtil;
import be.bds.bdsbes.utils.ResponseUtil;
import be.bds.bdsbes.utils.ServiceExceptionBuilderUtil;
import be.bds.bdsbes.utils.ValidationErrorUtil;
import be.bds.bdsbes.utils.dto.ValidationErrorResponse;
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
@RequestMapping("/rpc/bds/dat-phong")
public class DatPhongController {

    @Autowired
    IDatPhongService iDatPhongService;

    @GetMapping("/list")
    public ResponseEntity<?> getList(
            @RequestParam(value = "page", defaultValue = AppConstantsUtil.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstantsUtil.DEFAULT_PAGE_SIZE) int size) {
        try {
            return ResponseUtil.wrap(this.iDatPhongService.getRoomOrder(page, size));
        } catch (Exception ex) {
            log.error(this.getClass().getName(), ex);
            return ResponseUtil.generateErrorResponse(ex);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("get-one/{id}")
    public ResponseEntity<?> getOne(@PathVariable("id") Long id) {
        if (iDatPhongService.getOne(id) == null) {
            return ResponseEntity.badRequest().body("Không tồn tại");
        }
        return ResponseEntity.ok(iDatPhongService.getOne(id));
    }

    @PostMapping("create")
    public ResponseEntity<?> create(@Valid @RequestBody DatPhongDTO datPhongDTO) {
        try {
            Boolean response = iDatPhongService.create(datPhongDTO);
            return ResponseUtil.wrap(response);
        } catch (ServiceException e) {
            log.error(this.getClass().getName(), e);
            return ResponseUtil.generateErrorResponse(e);
        }

    }

    @PutMapping("update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @Valid @RequestBody DatPhongDTO datPhongDTO, BindingResult result) {
        if (iDatPhongService.update(datPhongDTO, id) == null) {
            return ResponseEntity.badRequest().body("Không tìm thấy");
        }
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            return ResponseEntity.ok(errors);
        } else {
            return ResponseEntity.ok().body(iDatPhongService.update(datPhongDTO, id));
        }
    }
}

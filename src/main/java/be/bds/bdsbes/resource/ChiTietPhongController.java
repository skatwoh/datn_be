package be.bds.bdsbes.resource;

import be.bds.bdsbes.exception.ServiceException;
import be.bds.bdsbes.service.IChiTietPhongService;
import be.bds.bdsbes.service.dto.ChiTietPhongDTO;
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
@RequestMapping("/rpc/bds/chi-tiet-phong")
public class ChiTietPhongController {

    @Autowired
    IChiTietPhongService iChiTietPhongService;

    @GetMapping("list")
    public ResponseEntity<?> getList(
            @RequestParam(value = "page", defaultValue = AppConstantsUtil.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstantsUtil.DEFAULT_PAGE_SIZE) int size) {
        try {
            return ResponseUtil.wrap(this.iChiTietPhongService.getChiTietPhong(page, size));
        } catch (Exception ex) {
            log.error(this.getClass().getName(), ex);
            return ResponseUtil.generateErrorResponse(ex);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("detail")
    public ResponseEntity<?> getOne(@PathVariable("id") Long id){
        if(iChiTietPhongService.getOne(id) == null){
            return ResponseEntity.badRequest().body("Không tìm thấy");
        }
        return ResponseEntity.ok(iChiTietPhongService.getOne(id));
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody @Valid ChiTietPhongDTO chiTietPhongDTO, BindingResult result){
        if(result.hasErrors()){
            List<ObjectError> errorList = result.getAllErrors();
            return ResponseEntity.badRequest().body(errorList);
        }
        return ResponseEntity.ok(iChiTietPhongService.create(chiTietPhongDTO));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> create(@PathVariable("id") Long id, @RequestBody @Valid ChiTietPhongDTO chiTietPhongDTO, BindingResult result){
        if(iChiTietPhongService.update(chiTietPhongDTO, id) == null){
            return ResponseEntity.badRequest().body("Cập nhật thất bại");
        }
        if(result.hasErrors()){
            List<ObjectError> errorList = result.getAllErrors();
            return ResponseEntity.badRequest().body(errorList);
        }
        return ResponseEntity.ok(iChiTietPhongService.update(chiTietPhongDTO, id));
    }
}

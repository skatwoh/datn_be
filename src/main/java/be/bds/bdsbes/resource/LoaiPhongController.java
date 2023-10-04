package be.bds.bdsbes.resource;


import be.bds.bdsbes.entities.LoaiPhong;
import be.bds.bdsbes.exception.ServiceException;
import be.bds.bdsbes.service.ILoaiPhongService;
import be.bds.bdsbes.service.dto.LoaiPhongDTO;
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
@RequestMapping("/rpc/bds/loai-phong")
public class LoaiPhongController {

    @Autowired
    ILoaiPhongService iLoaiPhongService;

    @GetMapping("list")
    public ResponseEntity<?> getList(
            @RequestParam(value = "page", defaultValue = AppConstantsUtil.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstantsUtil.DEFAULT_PAGE_SIZE) int size) {
        try {
            return ResponseUtil.wrap(this.iLoaiPhongService.getLoaiPhong(page, size));
        } catch (Exception ex) {
            log.error(this.getClass().getName(), ex);
            return ResponseUtil.generateErrorResponse(ex);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody @Valid LoaiPhongDTO loaiPhongDTO, BindingResult result){
        if(result.hasErrors()){
            List<ObjectError> errorList = result.getAllErrors();
            return ResponseEntity.badRequest().body(errorList);
        }
        List<LoaiPhong> list = iLoaiPhongService.getList();
        if(list.size() > 0){
            for(LoaiPhong loaiPhong : list) {
                if(loaiPhong.getMaLoaiPhong().equalsIgnoreCase(loaiPhongDTO.getMaLoaiPhong())){
                    return  ResponseEntity.badRequest().body("Mã loại phòng đã tồn tại");
                }
            }
        }
        return ResponseEntity.ok(iLoaiPhongService.create(loaiPhongDTO));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> create(@PathVariable("id") Long id, @RequestBody @Valid LoaiPhongDTO loaiPhongDTO, BindingResult result){
        if(result.hasErrors()){
            List<ObjectError> errorList = result.getAllErrors();
            return ResponseEntity.badRequest().body(errorList);
        }
        if(iLoaiPhongService.update(loaiPhongDTO, id) == null){
            return ResponseEntity.badRequest().body("Khong tim thay loai phong co id " + id);
        }
        List<LoaiPhong> list = iLoaiPhongService.getList();
        if(list.size() > 0){
            for(LoaiPhong loaiPhong : list) {
                if(loaiPhong.getMaLoaiPhong().equalsIgnoreCase(loaiPhongDTO.getMaLoaiPhong()) && !loaiPhong.getId().equals(id)){
                    return  ResponseEntity.badRequest().body("Mã loại phòng đã tồn tại");
                }
            }
        }
        return ResponseEntity.ok(iLoaiPhongService.update(loaiPhongDTO, id));
    }
}

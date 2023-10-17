package be.bds.bdsbes.resource;

import be.bds.bdsbes.entities.Phong;
import be.bds.bdsbes.exception.ServiceException;
import be.bds.bdsbes.service.ILoaiPhongService;
import be.bds.bdsbes.service.IPhongService;
import be.bds.bdsbes.service.dto.PhongDTO;
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
@RequestMapping("/rpc/bds/phong")
public class PhongController {

    @Autowired
    IPhongService iPhongService;

    @Autowired
    ILoaiPhongService iLoaiPhongService;

    @GetMapping("list")
    public ResponseEntity<?> getList(
            @RequestParam(value = "page", defaultValue = AppConstantsUtil.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstantsUtil.DEFAULT_PAGE_SIZE) int size) {
        try {
            return ResponseUtil.wrap(this.iPhongService.getPhong(page, size));
        } catch (Exception ex) {
            log.error(this.getClass().getName(), ex);
            return ResponseUtil.generateErrorResponse(ex);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("single-list-room-type")
    public ResponseEntity<?> singleListRoomType(){
        return ResponseEntity.ok(iLoaiPhongService.singleListRoomType());
    }

    @GetMapping("detail")
    public ResponseEntity<?> getOne(@RequestParam(value = "id") Long id){
        return ResponseEntity.ok(iPhongService.get(id));
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody @Valid PhongDTO phongDTO, BindingResult result){
        if(result.hasErrors()){
            List<ObjectError> errorList = result.getAllErrors();
            return ResponseEntity.badRequest().body(errorList);
        }
        List<Phong> list = iPhongService.getList();
        for(Phong phong : list){
            if(phong.getMa().equalsIgnoreCase(phongDTO.getMa())){
                return ResponseEntity.badRequest().body("Mã phòng " + phong.getMa() + " đã tồn tại");
            }
        }
        return ResponseEntity.ok(iPhongService.create(phongDTO));
    }

    @PutMapping("update")
    public ResponseEntity<?> update(@RequestParam(value = "id") Long id, @RequestBody @Valid PhongDTO phongDTO, BindingResult result){
        if(result.hasErrors()){
            List<ObjectError> errorList = result.getAllErrors();
            return ResponseEntity.badRequest().body(errorList);
        }
        if(iPhongService.update(phongDTO, id) == null){
            return ResponseEntity.badRequest().body("Không tìm thấy");
        }
        List<Phong> list = iPhongService.getList();
        for(Phong phong : list){
            if(phong.getMa().equalsIgnoreCase(phongDTO.getMa()) && !phong.getId().equals(id)){
                return ResponseEntity.badRequest().body("Mã phòng " + phong.getMa() + " trùng với một phòng khác đang tồn tại");
            }
        }
        return ResponseEntity.ok(iPhongService.update(phongDTO, id));
    }
}

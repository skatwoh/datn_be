package be.bds.bdsbes.resource;


import be.bds.bdsbes.entities.LoaiPhong;
import be.bds.bdsbes.exception.ServiceException;
import be.bds.bdsbes.service.iService.ILoaiPhongService;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    @GetMapping("detail")
    public ResponseEntity<?> getOne(@RequestParam(value = "id") Long id) {
        return ResponseEntity.ok(iLoaiPhongService.get(id));
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

    @PutMapping("update")
    public ResponseEntity<?> update(@RequestParam(value = "id") Long id, @RequestBody @Valid LoaiPhongDTO loaiPhongDTO, BindingResult result){
        if(result.hasErrors()){
            List<ObjectError> errorList = result.getAllErrors();
            return ResponseEntity.badRequest().body(errorList);
        }
        return ResponseEntity.ok(iLoaiPhongService.update(loaiPhongDTO, id));
    }

    @GetMapping("list-by-so-phong")
    public ResponseEntity<?> listBySoPhongAndSoNguoi(@RequestParam(value = "soPhong") int soPhong,
                                                     @RequestParam(value = "soNguoi") int soNguoi,
                                                     @RequestParam(value = "checkIn") String checkIn,
                                                     @RequestParam(value = "checkOut") String checkOut) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime parsedCheckIn = LocalDate.parse(checkIn, formatter).atStartOfDay();
        LocalDateTime parsedCheckOut = LocalDate.parse(checkOut, formatter).atStartOfDay();
        if(soNguoi%soPhong != 0) {
            return ResponseEntity.ok(iLoaiPhongService.listLoaiPhongBySoNguoiAndSoPhong2(soPhong, soNguoi/soPhong, parsedCheckIn, parsedCheckOut));
        }
        return ResponseEntity.ok(iLoaiPhongService.listLoaiPhongBySoNguoiAndSoPhong1(soPhong, soNguoi/soPhong, parsedCheckIn, parsedCheckOut));
    }
}

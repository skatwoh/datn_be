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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/rpc/bds/phong")
public class PhongController {

    @Autowired
    IPhongService iPhongService;

    @Autowired
    ILoaiPhongService iLoaiPhongService;

//    @Scheduled(cron = "*/5 * * * * *")
//    public void executeTask() {
//            System.out.println("hello");
//    }

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

    @GetMapping("list-room")
    public ResponseEntity<?> getListRoom(
            @RequestParam(value = "page", defaultValue = AppConstantsUtil.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstantsUtil.DEFAULT_PAGE_SIZE) int size) {
        try {
            return ResponseUtil.wrap(this.iPhongService.getListRoom(page, size));
        } catch (Exception ex) {
            log.error(this.getClass().getName(), ex);
            return ResponseUtil.generateErrorResponse(ex);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("sort-by-id-desc")
    public ResponseEntity<?> sortById(
            @RequestParam(value = "page", defaultValue = AppConstantsUtil.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstantsUtil.DEFAULT_PAGE_SIZE) int size) {
        try {
            return ResponseUtil.wrap(this.iPhongService.getPhongSortbyId(page, size));
        } catch (Exception ex) {
            log.error(this.getClass().getName(), ex);
            return ResponseUtil.generateErrorResponse(ex);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("search")
    public ResponseEntity<?> getListbySearch(
            @RequestParam(value = "page", defaultValue = AppConstantsUtil.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstantsUtil.DEFAULT_PAGE_SIZE) int size,
            @RequestParam(value = "input", defaultValue = "") String searchInput) {
        try {
            return ResponseUtil.wrap(this.iPhongService.searchRoom(page, size, searchInput));
        } catch (Exception ex) {
            log.error(this.getClass().getName(), ex);
            return ResponseUtil.generateErrorResponse(ex);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("single-list-room-type")
    public ResponseEntity<?> singleListRoomType() {
        return ResponseEntity.ok(iLoaiPhongService.singleListRoomType());
    }

    @GetMapping("detail")
    public ResponseEntity<?> getOne(@RequestParam(value = "id") Long id) {
        return ResponseEntity.ok(iPhongService.get(id));
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody @Valid PhongDTO phongDTO) throws ServiceException{
//        if (result.hasErrors()) {
//            List<ObjectError> errorList = result.getAllErrors();
//            return ResponseEntity.badRequest().body(errorList);
//        }
        try {
            return ResponseUtil.wrap(this.iPhongService.create(phongDTO));
        } catch (ServiceException e) {
            log.error(this.getClass().getName(), e);
            return ResponseUtil.generateErrorResponse(e);
        } catch (Exception ex) {
            log.error(this.getClass().getName(), ex);
            return ResponseUtil.generateErrorResponse(ex);
        }
    }

    @PutMapping("update")
    public ResponseEntity<?> update(@RequestParam(value = "id") Long id, @RequestBody @Valid PhongDTO phongDTO, BindingResult result) {
        if (result.hasErrors()) {
            List<ObjectError> errorList = result.getAllErrors();
            return ResponseEntity.badRequest().body(errorList);
        }
        List<Phong> list = iPhongService.getList();
        for (Phong phong : list) {
            if (phong.getMa().equalsIgnoreCase(phongDTO.getMa()) && !phong.getId().equals(id)) {
                return ResponseEntity.badRequest().body("Mã phòng " + phong.getMa() + " trùng với một phòng khác đang tồn tại");
            }
        }
        return ResponseEntity.ok(iPhongService.update(phongDTO, id));
    }

    @PutMapping("delete")
    public ResponseEntity<?> delete(@RequestParam(value = "id") Long id) {
        return ResponseEntity.ok(this.iPhongService.updateTrangThai(id));
    }

    @GetMapping("get-room-by-search")
    public ResponseEntity<?> getListByAll(
            @RequestParam(value = "page", defaultValue = AppConstantsUtil.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstantsUtil.DEFAULT_PAGE_SIZE) int size,
            @RequestParam(value = "input", defaultValue = "") String tenLoaiPhong,
            @RequestParam(value = "checkIn", defaultValue = "") String checkIn,
            @RequestParam(value = "checkOut", defaultValue = "") String checkOut) {
        try {
            if(checkIn.equals("") || checkOut.equals("") || checkIn == null || checkOut == null){
                return ResponseUtil.wrap(this.iPhongService.searchRoomManager2(page, size, tenLoaiPhong));
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDateTime parsedCheckIn = LocalDate.parse(checkIn, formatter).atStartOfDay();
            LocalDateTime parsedCheckOut = LocalDate.parse(checkOut, formatter).atStartOfDay();
            System.out.println(parsedCheckOut + " " + parsedCheckIn);
            return ResponseUtil.wrap(this.iPhongService.searchRoomManager(page, size, tenLoaiPhong, parsedCheckIn, parsedCheckOut));
        } catch (Exception ex) {
            log.error(this.getClass().getName(), ex);

            return ResponseUtil.generateErrorResponse(ex);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }
}

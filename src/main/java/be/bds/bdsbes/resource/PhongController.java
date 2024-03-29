package be.bds.bdsbes.resource;

import be.bds.bdsbes.entities.Phong;
import be.bds.bdsbes.exception.ServiceException;
import be.bds.bdsbes.service.iService.ILoaiPhongService;
import be.bds.bdsbes.service.iService.IPhongService;
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

    @GetMapping("list-room-same")
    public ResponseEntity<?> getListSameRoom(
            @RequestParam(value = "page", defaultValue = AppConstantsUtil.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstantsUtil.DEFAULT_PAGE_SIZE) int size,
            @RequestParam(value = "idPhong", defaultValue = "") Long idPhong) {
        try {
            return ResponseUtil.wrap(this.iPhongService.getListSameRoom(page, size, idPhong));
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
    public ResponseEntity<?> create(@RequestBody @Valid PhongDTO phongDTO) throws ServiceException {
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
            @RequestParam(value = "soLuongNguoi", defaultValue = "") String soLuongNguoi,
            @RequestParam(value = "input", defaultValue = "") String tenLoaiPhong,
            @RequestParam(value = "checkIn", defaultValue = "") String checkIn,
            @RequestParam(value = "checkOut", defaultValue = "") String checkOut,
            @RequestParam(value = "minGia", defaultValue = "") BigDecimal minGia,
            @RequestParam(value = "maxGia", defaultValue = "") BigDecimal maxGia) {
        try {
            if (tenLoaiPhong.isEmpty() && (checkIn.isEmpty() && checkOut.isEmpty())) {
                return ResponseUtil.wrap(this.iPhongService.getPhong(page, size));
            }
            if (tenLoaiPhong != null && !soLuongNguoi.isEmpty() && Integer.valueOf(soLuongNguoi) == 4 && (checkIn.equals("") || checkOut.equals("") || checkIn == null || checkOut == null)) {
                return ResponseUtil.wrap(this.iPhongService.searchRoomManager2(page, size, Integer.valueOf(soLuongNguoi), tenLoaiPhong));
            }
            if (tenLoaiPhong != null && !soLuongNguoi.isEmpty() && Integer.valueOf(soLuongNguoi) < 4 && (checkIn.equals("") || checkOut.equals("") || checkIn == null || checkOut == null)) {
                return ResponseUtil.wrap(this.iPhongService.searchRoomManager4(page, size, Integer.valueOf(soLuongNguoi), tenLoaiPhong));
            }
            if(soLuongNguoi.isEmpty() && tenLoaiPhong.isEmpty() && !checkIn.isEmpty() && !checkOut.isEmpty()){
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDateTime parsedCheckIn = LocalDate.parse(checkIn, formatter).atStartOfDay();
                LocalDateTime parsedCheckOut = LocalDate.parse(checkOut, formatter).atStartOfDay();
                return ResponseUtil.wrap(this.iPhongService.getListRoomByCheckDate(page, size, parsedCheckIn, parsedCheckOut));
            }
            if(soLuongNguoi.isEmpty() && !tenLoaiPhong.isEmpty() && !checkIn.isEmpty() && !checkOut.isEmpty()){
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDateTime parsedCheckIn = LocalDate.parse(checkIn, formatter).atStartOfDay();
                LocalDateTime parsedCheckOut = LocalDate.parse(checkOut, formatter).atStartOfDay();
                return ResponseUtil.wrap(this.iPhongService.getListRoomByCheckDateandLoaiPhong(page, size, tenLoaiPhong, parsedCheckIn, parsedCheckOut));
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDateTime parsedCheckIn = LocalDate.parse(checkIn, formatter).atStartOfDay();
            LocalDateTime parsedCheckOut = LocalDate.parse(checkOut, formatter).atStartOfDay();
            System.out.println(parsedCheckOut + " " + parsedCheckIn);
            if (!soLuongNguoi.isEmpty() &&
                    Integer.valueOf(soLuongNguoi) == 4) {
                return ResponseUtil.wrap(this.iPhongService.searchRoomManager3(page, size, Integer.valueOf(soLuongNguoi), tenLoaiPhong, parsedCheckIn, parsedCheckOut));
            }
            return ResponseUtil.wrap(this.iPhongService.searchRoomManager(page, size, Integer.valueOf(soLuongNguoi), tenLoaiPhong, parsedCheckIn, parsedCheckOut));
        } catch (Exception | ServiceException ex) {
            log.error(this.getClass().getName(), ex);

            return ResponseUtil.generateErrorResponse((ServiceException) ex);
        }
    }

    @GetMapping("get-room-by-price")
    public ResponseEntity<?> getListByPrice(
            @RequestParam(value = "page", defaultValue = AppConstantsUtil.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstantsUtil.DEFAULT_PAGE_SIZE) int size,
            @RequestParam(value = "minGia", defaultValue = "") BigDecimal minGia,
            @RequestParam(value = "maxGia", defaultValue = "") BigDecimal maxGia) {
        return ResponseUtil.wrap(this.iPhongService.searchRoomManagerByPrice(page, size, minGia, maxGia));

    }

    @GetMapping("list-top-room-booking")
    public ResponseEntity<?> getListTopRoomBooking(
            @RequestParam(value = "page", defaultValue = AppConstantsUtil.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstantsUtil.DEFAULT_PAGE_SIZE) int size) {
        try {
            return ResponseUtil.wrap(this.iPhongService.getListTopRoomOrder(page, size));
        } catch (Exception ex) {
            log.error(this.getClass().getName(), ex);
            return ResponseUtil.generateErrorResponse(ex);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("list-room-of-floar")
    public ResponseEntity<?> getListRoomOfFloar(
            @RequestParam(value = "page", defaultValue = AppConstantsUtil.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstantsUtil.DEFAULT_PAGE_SIZE) int size) {
        try {
            return ResponseUtil.wrap(this.iPhongService.getListRoomOfFloar(page, size));
        } catch (Exception ex) {
            log.error(this.getClass().getName(), ex);
            return ResponseUtil.generateErrorResponse(ex);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("get-room-by-string")
    public ResponseEntity<?> getRoombySearch(
            @RequestParam(value = "page", defaultValue = AppConstantsUtil.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstantsUtil.DEFAULT_PAGE_SIZE) int size,
            @RequestParam(value = "searchInput", defaultValue = "") String searchInput) {
        try {
            return ResponseUtil.wrap(this.iPhongService.searchRoomByString(page, size, searchInput));
        } catch (Exception ex) {
            log.error(this.getClass().getName(), ex);
            return ResponseUtil.generateErrorResponse(ex);
        }
    }

    @GetMapping("list-room-active")
    public ResponseEntity<?> getListRoomActive(
            @RequestParam(value = "page", defaultValue = AppConstantsUtil.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstantsUtil.DEFAULT_PAGE_SIZE) int size,
            @RequestParam(value = "checkIn", defaultValue = "") String checkIn,
            @RequestParam(value = "checkOut", defaultValue = "") String checkOut) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDateTime parsedCheckIn = LocalDate.parse(checkIn, formatter).atStartOfDay();
            LocalDateTime parsedCheckOut = LocalDate.parse(checkOut, formatter).atStartOfDay();
            return ResponseUtil.wrap(this.iPhongService.getListRoomActive(page, size, parsedCheckIn, parsedCheckOut));
        } catch (Exception ex) {
            log.error(this.getClass().getName(), ex);
            return ResponseUtil.generateErrorResponse(ex);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("get-room-by-tienIch")
    public ResponseEntity<?> getRoombyTienIch(
            @RequestParam(value = "page", defaultValue = AppConstantsUtil.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstantsUtil.DEFAULT_PAGE_SIZE) int size,
            @RequestParam(value = "searchInput", defaultValue = "") List<String> tienIch,
            @RequestParam(value = "soLuongNguoi", defaultValue = "") String soLuongNguoi,
            @RequestParam(value = "input", defaultValue = "") String tenLoaiPhong,
            @RequestParam(value = "checkIn", defaultValue = "") String checkIn,
            @RequestParam(value = "checkOut", defaultValue = "") String checkOut) {

        try {
            if(tienIch.size() <= 0 && (checkIn.equals("") || checkOut.equals("")) && !tenLoaiPhong.isEmpty()){
                System.out.println("check1");
                return ResponseUtil.wrap(this.iPhongService.getListRoomByLoaiPhong(page, size, tenLoaiPhong));
            }
            if(tienIch.size() <= 0 && (!checkIn.equals("") || !checkOut.equals("")) && tenLoaiPhong.isEmpty()){
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDateTime parsedCheckIn = LocalDate.parse(checkIn, formatter).atStartOfDay();
                LocalDateTime parsedCheckOut = LocalDate.parse(checkOut, formatter).atStartOfDay();
                System.out.println("check2");
                return ResponseUtil.wrap(this.iPhongService.getListRoomByCheckDate(page, size, parsedCheckIn, parsedCheckOut));
            }
            if(tienIch.size() <= 0 && (!checkIn.equals("") || !checkOut.equals("")) && !tenLoaiPhong.isEmpty()){
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDateTime parsedCheckIn = LocalDate.parse(checkIn, formatter).atStartOfDay();
                LocalDateTime parsedCheckOut = LocalDate.parse(checkOut, formatter).atStartOfDay();
                System.out.println("check2");
                return ResponseUtil.wrap(this.iPhongService.getListRoomByCheckDateandLoaiPhong(page, size, tenLoaiPhong, parsedCheckIn, parsedCheckOut));
            }
            if(tienIch.size() > 0 && (!checkIn.equals("") || !checkOut.equals("")) && tenLoaiPhong.isEmpty()){
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDateTime parsedCheckIn = LocalDate.parse(checkIn, formatter).atStartOfDay();
                LocalDateTime parsedCheckOut = LocalDate.parse(checkOut, formatter).atStartOfDay();
                System.out.println("check2");
                return ResponseUtil.wrap(this.iPhongService.getListRoomByCheckDateandTienIch(page, size, tienIch, parsedCheckIn, parsedCheckOut));
            }
            if(tienIch.size() > 0 && (!checkIn.equals("") || !checkOut.equals("")) && !tenLoaiPhong.isEmpty()){
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDateTime parsedCheckIn = LocalDate.parse(checkIn, formatter).atStartOfDay();
                LocalDateTime parsedCheckOut = LocalDate.parse(checkOut, formatter).atStartOfDay();
                System.out.println("check2");
                return ResponseUtil.wrap(this.iPhongService.getListRoomByCheckDateandAll(page, size, tienIch, tenLoaiPhong, parsedCheckIn, parsedCheckOut));
            }
            if ((checkIn.equals("") || checkOut.equals("")) && soLuongNguoi.equals("") && tenLoaiPhong.isEmpty()){
                System.out.println("check3");
                return ResponseUtil.wrap(this.iPhongService.getListRoomByTienIch(page, size, tienIch));
            }
            if ((checkIn.equals("") || checkOut.equals("")) && tenLoaiPhong.isEmpty() && !soLuongNguoi.isEmpty()){
                return ResponseUtil.wrap(this.iPhongService.getListRoomByTienIch1(page, size, tienIch,Integer.parseInt(soLuongNguoi)));
            }
            if ((checkIn.equals("") || checkOut.equals("")) && !tenLoaiPhong.isEmpty() && soLuongNguoi.isEmpty()){
                return ResponseUtil.wrap(this.iPhongService.getListRoomByTienIch2(page, size, tienIch,tenLoaiPhong));
            }
            if ((checkIn.equals("") || checkOut.equals("")) && !tenLoaiPhong.isEmpty() && !soLuongNguoi.isEmpty() && tienIch.size() <= 0){
                return ResponseUtil.wrap(this.iPhongService.searchRoomManager4(page, size, Integer.valueOf(soLuongNguoi), tenLoaiPhong));
            }
             return ResponseUtil.wrap(this.iPhongService.getListRoomByTienIch3(page, size, tienIch,tenLoaiPhong,Integer.parseInt(soLuongNguoi)));
        } catch (Exception | ServiceException ex) {
            log.error(this.getClass().getName(), ex);
            return ResponseUtil.generateErrorResponse((ServiceException) ex);
        }
    }

    @GetMapping("get-room-by-lp")
    public ResponseEntity<?> getRoombyLoaiPhong(
            @RequestParam(value = "page", defaultValue = AppConstantsUtil.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstantsUtil.DEFAULT_PAGE_SIZE) int size,
            @RequestParam(value = "input", defaultValue = "") String tenLoaiPhong) throws ServiceException {
        return ResponseUtil.wrap(this.iPhongService.getListRoomByLoaiPhong(page, size, tenLoaiPhong));
    }
}

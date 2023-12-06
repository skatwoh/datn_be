package be.bds.bdsbes.resource;

import be.bds.bdsbes.exception.ServiceException;
import be.bds.bdsbes.service.iService.IChiTietPhongService;
import be.bds.bdsbes.service.iService.IPhongService;
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

    @Autowired
    IPhongService iPhongService;

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

    @GetMapping("sort-by-id-desc")
    public ResponseEntity<?> sortById(
            @RequestParam(value = "page", defaultValue = AppConstantsUtil.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstantsUtil.DEFAULT_PAGE_SIZE) int size) {
        try {
            return ResponseUtil.wrap(this.iChiTietPhongService.getChiTietPhongSortbyId(page, size));
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
            return ResponseUtil.wrap(this.iChiTietPhongService.searchRoom(page, size, searchInput));
        } catch (Exception ex) {
            log.error(this.getClass().getName(), ex);
            return ResponseUtil.generateErrorResponse(ex);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("single-list-room")
    public ResponseEntity<?> singleListRoom() {
        return ResponseEntity.ok(iPhongService.singleListRoom());
    }

    @GetMapping("detail")
    public ResponseEntity<?> getOne(@RequestParam(value = "id") Long id) {
//        if(iChiTietPhongService.getOne(id) == null){
//            return ResponseEntity.badRequest().body("Không tìm thấy");
//        }
        return ResponseEntity.ok(iChiTietPhongService.get(id));
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody @Valid ChiTietPhongDTO chiTietPhongDTO, BindingResult result) {
        if (result.hasErrors()) {
            List<ObjectError> errorList = result.getAllErrors();
            return ResponseEntity.badRequest().body(errorList);
        }
        return ResponseEntity.ok(iChiTietPhongService.create(chiTietPhongDTO));
    }

    @PutMapping("/update")
    public ResponseEntity<?> create(@RequestParam(value = "id") Long id, @RequestBody @Valid ChiTietPhongDTO chiTietPhongDTO, BindingResult result) {
        if (result.hasErrors()) {
            List<ObjectError> errorList = result.getAllErrors();
            return ResponseEntity.badRequest().body(errorList);
        }
        return ResponseEntity.ok(iChiTietPhongService.update(chiTietPhongDTO, id));
    }

    @PutMapping("/delete")
    public ResponseEntity<?> create(@RequestParam(value = "id") Long id) {
        return ResponseEntity.ok(iChiTietPhongService.updateTrangThai(id));
    }

    @GetMapping("get-room")
    public ResponseEntity<?> getRoom(@RequestParam(value = "idPhong") Long idPhong) {
        return ResponseEntity.ok(iChiTietPhongService.getCTP(idPhong));
    }

    @GetMapping("find-by-so-nguoi")
    public ResponseEntity<?> findBySoNguoi(
            @RequestParam(value = "page", defaultValue = AppConstantsUtil.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstantsUtil.DEFAULT_PAGE_SIZE) int size,
            @RequestParam(value = "soNguoi") Integer soNguoi,
            @RequestParam(value = "soPhongCan") Integer soPhongCan
            ) {
        try {
            return ResponseUtil.wrap(this.iChiTietPhongService.searchRoomBySoNguoi(page, size, soNguoi, soPhongCan));
        } catch (Exception ex) {
            log.error(this.getClass().getName(), ex);
            return ResponseUtil.generateErrorResponse(ex);
        }
    }
}

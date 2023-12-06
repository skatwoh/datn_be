package be.bds.bdsbes.resource;

import be.bds.bdsbes.entities.TaiKhoan;
import be.bds.bdsbes.exception.ServiceException;
import be.bds.bdsbes.repository.TaiKhoanRepository;
import be.bds.bdsbes.service.iService.ITaiKhoanService;
import be.bds.bdsbes.service.dto.LoginDTO;
import be.bds.bdsbes.service.dto.TaiKhoanDTO;
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
@RequestMapping("/rpc/bds/tai-khoan")
public class TaiKhoanController {

    @Autowired
    ITaiKhoanService iTaiKhoanService;
    @Autowired
    private TaiKhoanRepository taiKhoanRepository;

    @GetMapping("list1")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(iTaiKhoanService.getAllTaiKhoan());
    }

    @GetMapping("get-page")
    public ResponseEntity<?> getPage(@RequestParam(defaultValue = "0", name = "page") Integer page){
        return ResponseEntity.ok(iTaiKhoanService.getPage(page).toList());
    }

//    @GetMapping("detail/{id}")
//    public ResponseEntity<?> getOne(@PathVariable("id") String id){
//        try {
//            if(iTaiKhoanService.getOne(Long.parseLong(id)) == null){
//                return ResponseEntity.badRequest().body("Không tìm thấy");
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//            return ResponseEntity.ok("id must be a number");
//        }
//        return ResponseEntity.ok(iTaiKhoanService.getOne(Long.parseLong(id)));
//    }

    @GetMapping("detail")
    public ResponseEntity<?> get(@RequestParam(value = "id") Long id){
//        try {
//            if(iTaiKhoanService.get(id) == null){
//                return ResponseEntity.badRequest().body("Không tìm thấy");
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//            return ResponseEntity.ok("id must be a number");
//        }
        return ResponseUtil.wrap(this.iTaiKhoanService.get(id));
    }

    @PostMapping("create")
    public ResponseEntity<?> add(@RequestBody @Valid TaiKhoanDTO taiKhoanDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            List<ObjectError> errorList = bindingResult.getAllErrors();
            return ResponseEntity.ok(errorList);
        }
        List<TaiKhoan> listTK = taiKhoanRepository.findAll();
        if(listTK.size() > 0){
            for (TaiKhoan t : listTK) {
                if(t.getEmail().equals(taiKhoanDTO.getEmail())){
                    return ResponseEntity.badRequest().body("Email " + taiKhoanDTO.getEmail() + " đã tồn tại");
                }
            }
        }
        return ResponseEntity.ok(iTaiKhoanService.add(taiKhoanDTO));
    }

    @PutMapping("update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody @Valid TaiKhoanDTO taiKhoanDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            List<ObjectError> errorList = bindingResult.getAllErrors();
            return ResponseEntity.ok(errorList);
        }
        List<TaiKhoan> listTK = taiKhoanRepository.findAll();
        if(listTK.size() > 0){
            for (TaiKhoan t : listTK) {
                if(t.getEmail().equals(taiKhoanDTO.getEmail()) && !t.getId().equals(id)){
                    return ResponseEntity.badRequest().body("Email " + taiKhoanDTO.getEmail() + " đã tồn tại");
                }
            }
        }
        if(iTaiKhoanService.update(taiKhoanDTO, id) == null){
            return ResponseEntity.badRequest().body("Không tìm thấy");
        }
        return ResponseEntity.ok(iTaiKhoanService.update(taiKhoanDTO, id));
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginDTO loginDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            List<ObjectError> errorList = bindingResult.getAllErrors();
            return ResponseEntity.ok(errorList);
        }
        List<TaiKhoan> listTK = taiKhoanRepository.findAll();
        if(listTK.size() > 0){
            for (TaiKhoan t : listTK) {
                if(!t.getEmail().equals(loginDTO.getEmail()) && !t.getMatKhau().equals(loginDTO.getMatKhau())){
                    return ResponseEntity.badRequest().body("Vui lòng nhập lại tài khoản");
                }
            }
        }
        return ResponseEntity.ok(iTaiKhoanService.login(loginDTO));
    }

    @GetMapping("/list")
    public ResponseEntity<?> getList(
            @RequestParam(value = "page", defaultValue = AppConstantsUtil.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstantsUtil.DEFAULT_PAGE_SIZE) int size) {
        try {
            return ResponseUtil.wrap(this.iTaiKhoanService.getAccounts(page, size));
        } catch (Exception ex) {
            log.error(this.getClass().getName(), ex);
            return ResponseUtil.generateErrorResponse(ex);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }

}

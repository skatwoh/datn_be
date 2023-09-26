package be.bds.bdsbes.resource;

import be.bds.bdsbes.entities.TaiKhoan;
import be.bds.bdsbes.repository.TaiKhoanRepository;
import be.bds.bdsbes.service.ITaiKhoanService;
import be.bds.bdsbes.service.dto.TaiKhoanDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/tai-khoan")
public class TaiKhoanController {

    @Autowired
    ITaiKhoanService ITaiKhoanService;
    @Autowired
    private TaiKhoanRepository taiKhoanRepository;

    @GetMapping("list")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(ITaiKhoanService.getAllTaiKhoan());
    }

    @GetMapping("get-page")
    public ResponseEntity<?> getPage(@RequestParam(defaultValue = "0", name = "page") Integer page){
        return ResponseEntity.ok(ITaiKhoanService.getPage(page).toList());
    }

    @GetMapping("detail/{id}")
    public ResponseEntity<?> getOne(@PathVariable("id") String id){
        try {
            if(ITaiKhoanService.getOne(Long.parseLong(id)) == null){
                return ResponseEntity.badRequest().body("Không tìm thấy");
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.ok("id must be a number");
        }
        return ResponseEntity.ok(ITaiKhoanService.getOne(Long.parseLong(id)));
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
        return ResponseEntity.ok(ITaiKhoanService.add(taiKhoanDTO));
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
        if(ITaiKhoanService.update(taiKhoanDTO, id) == null){
            return ResponseEntity.badRequest().body("Không tìm thấy");
        }
        return ResponseEntity.ok(ITaiKhoanService.update(taiKhoanDTO, id));
    }
}

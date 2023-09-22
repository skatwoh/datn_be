package be.bds.bdsbes.resource;

import be.bds.bdsbes.service.IKhachHangService;
import be.bds.bdsbes.service.dto.KhachHangDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/khach-hang")
public class KhachHangController {

    @Autowired
    IKhachHangService khachHangService;

    @GetMapping("list")
    public ResponseEntity<?> getList(){
        return ResponseEntity.ok(khachHangService.getList());
    }

    @GetMapping("detail/{id}")
    public ResponseEntity<?> getOne(@PathVariable("id") Long id){
        if(khachHangService.getOne(id) == null){
            return ResponseEntity.badRequest().body("Không tìm thấy");
        }
        return ResponseEntity.ok(khachHangService.getOne(id));
    }

    @PostMapping("create")
    public ResponseEntity<?> create(@RequestBody @Valid KhachHangDTO khachHangDTO, BindingResult result){
        if(result.hasErrors()){
            List<ObjectError> errorList = result.getAllErrors();
            return ResponseEntity.badRequest().body(errorList);
        }
        return ResponseEntity.ok(khachHangService.create(khachHangDTO));
    }

    @PutMapping("update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody @Valid KhachHangDTO khachHangDTO, BindingResult result){
        if(result.hasErrors()){
            List<ObjectError> errorList = result.getAllErrors();
            return ResponseEntity.badRequest().body(errorList);
        }
        return ResponseEntity.ok(khachHangService.update(khachHangDTO, id));
    }
}

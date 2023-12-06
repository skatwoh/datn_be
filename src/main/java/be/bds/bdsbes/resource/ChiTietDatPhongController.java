package be.bds.bdsbes.resource;

import be.bds.bdsbes.service.iService.IChiTietDatPhongService;
import be.bds.bdsbes.service.dto.ChiTietDatPhongDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/chi-tiet-dat-phong")
public class ChiTietDatPhongController {

    @Autowired
    IChiTietDatPhongService iChiTietDatPhongService;

    @GetMapping("/list")
    public ResponseEntity<?> getList(){
        return ResponseEntity.ok(iChiTietDatPhongService.getList());
    }

    @GetMapping("/get-page")
    public ResponseEntity<?> getPage(@RequestParam(defaultValue = "0", value = "page") Integer page){
        return ResponseEntity.ok(iChiTietDatPhongService.getPage(page));
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<?> getOne(@PathVariable("id") Long id){
        if(iChiTietDatPhongService.getOne(id) == null){
            return ResponseEntity.badRequest().body("Không tìm thây");
        }
        return ResponseEntity.ok(iChiTietDatPhongService.getOne(id));
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody @Valid ChiTietDatPhongDTO chiTietDatPhongDTO, BindingResult result){
        if(result.hasErrors()){
            List<ObjectError> errorList = result.getAllErrors();
            return ResponseEntity.badRequest().body(errorList);
        }
        return ResponseEntity.ok(iChiTietDatPhongService.create(chiTietDatPhongDTO));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody @Valid ChiTietDatPhongDTO chiTietDatPhongDTO, BindingResult result){
        if(result.hasErrors()){
            List<ObjectError> errorList = result.getAllErrors();
            return ResponseEntity.badRequest().body(errorList);
        }
        if(iChiTietDatPhongService.update(chiTietDatPhongDTO, id) == null){
            return ResponseEntity.badRequest().body("Cập nhật thất bại");
        }
        return ResponseEntity.ok(iChiTietDatPhongService.update(chiTietDatPhongDTO, id));
    }
}

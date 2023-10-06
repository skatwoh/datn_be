package be.bds.bdsbes.resource;

import be.bds.bdsbes.service.IDatPhongService;
import be.bds.bdsbes.service.dto.DatPhongDTO;
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
@RequestMapping("/api/dat-phong")
public class DatPhongController {

    @Autowired
    IDatPhongService iDatPhongService;

    @GetMapping("list")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(iDatPhongService.getAll());
    }

    @GetMapping("get-one/{id}")
    public ResponseEntity<?> getOne(@PathVariable("id") Long id){
        if(iDatPhongService.getOne(id) == null){
            return ResponseEntity.badRequest().body("Không tồn tại");
        }
        return ResponseEntity.ok(iDatPhongService.getOne(id));
    }

    @PostMapping("create")
    public ResponseEntity<?> create(@Valid @RequestBody DatPhongDTO datPhongDTO, BindingResult result){
        if(result.hasErrors()){
            List<ObjectError> errors = result.getAllErrors();
            return ResponseEntity.ok(errors);
        } else {
            return ResponseEntity.ok().body(iDatPhongService.create(datPhongDTO));
        }
    }

    @PutMapping("update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @Valid @RequestBody DatPhongDTO datPhongDTO, BindingResult result){
        if(iDatPhongService.update(datPhongDTO, id) == null){
            return ResponseEntity.badRequest().body("Không tìm thấy");
        }
        if(result.hasErrors()){
            List<ObjectError> errors = result.getAllErrors();
            return ResponseEntity.ok(errors);
        } else {
            return ResponseEntity.ok().body(iDatPhongService.update(datPhongDTO, id));
        }
    }
}

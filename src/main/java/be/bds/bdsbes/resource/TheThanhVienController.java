package be.bds.bdsbes.resource;


import be.bds.bdsbes.service.TheThanhVienService;
import be.bds.bdsbes.service.dto.TheThanhVienDTO;
import be.bds.bdsbes.service.impl.TheThanhVienServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/the-thanh-vien")
public class TheThanhVienController {

    @Autowired
    private TheThanhVienService theThanhVienService = new TheThanhVienServiceImpl();

    @GetMapping
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(theThanhVienService.getList());
    }

    @GetMapping("get-page")
    public ResponseEntity<?> getPage(@RequestParam(defaultValue = "0", name = "page") Integer page){
        return ResponseEntity.ok(theThanhVienService.getPage(page));
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getOne(@PathVariable("id") Long id){
        if(theThanhVienService.getOne(id) == null){
            return ResponseEntity.ok("Not found");
        }
        return ResponseEntity.ok(theThanhVienService.getOne(id));
    }

    @PostMapping("add")
    public ResponseEntity<?> add(@RequestBody @Valid TheThanhVienDTO theThanhVienDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            List<ObjectError> errorList = bindingResult.getAllErrors();
            return ResponseEntity.ok(errorList);
        }
        return ResponseEntity.ok(theThanhVienService.add(theThanhVienDTO));
    }

    @PutMapping("update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody @Valid TheThanhVienDTO theThanhVienDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            List<ObjectError> errorList = bindingResult.getAllErrors();
            return ResponseEntity.ok(errorList);
        }
        if(theThanhVienService.update(theThanhVienDTO, id) == null){
            return ResponseEntity.ok("Update failed");
        }
        return ResponseEntity.ok(theThanhVienService.update(theThanhVienDTO, id));
    }
}

package be.bds.bdsbes.resource;


import be.bds.bdsbes.service.iService.ITheThanhVienService;
import be.bds.bdsbes.service.dto.TheThanhVienDTO;
import be.bds.bdsbes.service.impl.ITheThanhVienServiceImpl;
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
    private ITheThanhVienService ITheThanhVienService = new ITheThanhVienServiceImpl();

    @GetMapping
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(ITheThanhVienService.getList());
    }

    @GetMapping("get-page")
    public ResponseEntity<?> getPage(@RequestParam(defaultValue = "0", name = "page") Integer page){
        return ResponseEntity.ok(ITheThanhVienService.getPage(page));
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getOne(@PathVariable("id") Long id){
        if(ITheThanhVienService.getOne(id) == null){
            return ResponseEntity.ok("Not found");
        }
        return ResponseEntity.ok(ITheThanhVienService.getOne(id));
    }

    @PostMapping("add")
    public ResponseEntity<?> add(@RequestBody @Valid TheThanhVienDTO theThanhVienDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            List<ObjectError> errorList = bindingResult.getAllErrors();
            return ResponseEntity.ok(errorList);
        }
        return ResponseEntity.ok(ITheThanhVienService.add(theThanhVienDTO));
    }

    @PutMapping("update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody @Valid TheThanhVienDTO theThanhVienDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            List<ObjectError> errorList = bindingResult.getAllErrors();
            return ResponseEntity.ok(errorList);
        }
        if(ITheThanhVienService.update(theThanhVienDTO, id) == null){
            return ResponseEntity.ok("Update failed");
        }
        return ResponseEntity.ok(ITheThanhVienService.update(theThanhVienDTO, id));
    }
}

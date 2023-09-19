package be.bds.bdsbes.resource;

import be.bds.bdsbes.service.IDatPhongService;
import be.bds.bdsbes.service.dto.DatPhongDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/dat-phong")
public class DatPhongController {

    @Autowired
    IDatPhongService iDatPhongService;

    @PostMapping("create")
    public ResponseEntity<?> create(@Valid @RequestBody DatPhongDTO datPhongDTO, BindingResult result){
        if(result.hasErrors()){
            List<ObjectError> errors = result.getAllErrors();
            return ResponseEntity.ok(errors);
        } else {
            return ResponseEntity.ok().body(iDatPhongService.create(datPhongDTO));
        }
    }
}

package be.bds.bdsbes.resource;

import be.bds.bdsbes.entities.Visitor;
import be.bds.bdsbes.service.impl.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

// Controller để xử lý các yêu cầu từ Angular
@RestController
@RequestMapping("/rpc/bds/api")
public class VisitorController {
    @Autowired
    private VisitorService visitorService;

    @PostMapping("record-visit")
    public void recordVisit(@RequestBody Visitor visitorRequest) {
        visitorService.recordVisit(visitorRequest.getPage(), visitorRequest.getIpAddress());
    }

    @GetMapping("get-unique-visitors-count")
    public int getUniqueVisitorsCount(@RequestParam String page) {
        return visitorService.getUniqueVisitorsCount(page);
    }

    @GetMapping("/get-visit-count")
    public int getVisitCount(@RequestParam String page) {
        return visitorService.getVisitCount(page);
    }
}

package be.bds.bdsbes.service.link;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final LinkService linkService;

    @Autowired
    public AdminController(LinkService linkService) {
        this.linkService = linkService;
    }

    @GetMapping("/list-router")
    public String getAdminLink() {
        return linkService.getLinksForAdmin().toString();
    }
}

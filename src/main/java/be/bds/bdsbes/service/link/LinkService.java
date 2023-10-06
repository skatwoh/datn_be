package be.bds.bdsbes.service.link;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LinkService {

    @PreAuthorize("hasRole('ADMIN')")
    public List<String> getLinksForAdmin() {
        List<String> links = new ArrayList<>();
        links.add("http://localhost:4200/admin/accounts");
        links.add("https://example.com/admin-link2");
        return links;
    }
}

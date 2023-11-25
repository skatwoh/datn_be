package be.bds.bdsbes.service.impl;

import be.bds.bdsbes.entities.Visitor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VisitorService {
    private List<Visitor> uniqueVisitors = new ArrayList<>();

    public void recordVisit(String page, String ipAddress) {
        // Kiểm tra xem người truy cập đã được ghi nhận trước đó chưa
        if (!isVisitorRecorded(page, ipAddress)) {
            Visitor visitor = new Visitor();
            visitor.setPage(page);
            visitor.setIpAddress(ipAddress);
            uniqueVisitors.add(visitor);
        }
    }

    public int getUniqueVisitorsCount(String page) {
        // Lọc danh sách người truy cập duy nhất cho từng trang
        return (int) uniqueVisitors.stream()
                .filter(visitor -> visitor.getPage().equals(page))
                .count();
    }

    private boolean isVisitorRecorded(String page, String ipAddress) {
        // Kiểm tra xem người truy cập đã được ghi nhận trước đó chưa
        return uniqueVisitors.stream()
                .anyMatch(visitor -> visitor.getPage().equals(page) && visitor.getIpAddress().equals(ipAddress));
    }
    public int getVisitCount(String page) {
        return (int) uniqueVisitors.stream()
                .filter(visitor -> visitor.getPage().equals(page))
                .count();
    }
}

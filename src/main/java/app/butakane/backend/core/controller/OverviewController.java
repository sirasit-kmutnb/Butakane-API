package app.butakane.backend.core.controller;

import com.example.financeapp.service.OverviewService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/overview")
public class OverviewController {

    @Autowired
    private OverviewService overviewService;

    @GetMapping("/all")
    public ResponseEntity<?> getOverview(HttpServletRequest request) {
        return overviewService.getOverview(request);
    }
}

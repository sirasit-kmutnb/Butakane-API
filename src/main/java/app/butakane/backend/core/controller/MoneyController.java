package app.butakane.backend.core.controller;

import app.butakane.backend.core.service.MoneyService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/money")
public class MoneyController {

    @Autowired
    private MoneyService moneyService;

    @GetMapping("/info")
    public ResponseEntity<?> getMoneyData(HttpServletRequest request) {
        return moneyService.getMoneyData(request);
    }

    @GetMapping("/income-summary")
    public ResponseEntity<?> getIncomeSummary(HttpServletRequest request) {
        return moneyService.getIncomeSummary(request);
    }

    @GetMapping("/outcome-summary")
    public ResponseEntity<?> getOutcomeSummary(HttpServletRequest request) {
        return moneyService.getOutcomeSummary(request);
    }
}


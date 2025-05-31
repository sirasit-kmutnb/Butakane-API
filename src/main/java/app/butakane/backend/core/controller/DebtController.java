package app.butakane.backend.core.controller;

import app.butakane.backend.core.model.request.DebtActionRequest;
import app.butakane.backend.core.model.request.DebtResolveRequest;
import app.butakane.backend.core.service.DebtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/debt")
public class DebtController {

    @Autowired
    private DebtService debtService;

    @GetMapping("/info")
    public ResponseEntity<?> getDebtData(HttpServletRequest request) {
        return debtService.getDebtData(request);
    }

    @GetMapping("/summary")
    public ResponseEntity<?> getDebtSummary(HttpServletRequest request) {
        return debtService.getDebtSummary(request);
    }

    @PostMapping("/borrow")
    public ResponseEntity<?> borrow(HttpServletRequest request, @RequestBody DebtActionRequest payload) {
        return debtService.borrowMoney(request, payload);
    }

    @PostMapping("/lend")
    public ResponseEntity<?> lend(HttpServletRequest request, @RequestBody DebtActionRequest payload) {
        return debtService.lendMoney(request, payload);
    }

    @PutMapping("/payback")
    public ResponseEntity<?> payBack(HttpServletRequest request, @RequestBody DebtResolveRequest payload) {
        return debtService.payBackDebt(request, payload);
    }

    @PutMapping("/receive-back")
    public ResponseEntity<?> receiveBack(HttpServletRequest request, @RequestBody DebtResolveRequest payload) {
        return debtService.receiveBackDebt(request, payload);
    }
}


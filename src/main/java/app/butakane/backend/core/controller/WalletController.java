package app.butakane.backend.core.controller;

import app.butakane.backend.core.model.request.IncomeRequest;
import app.butakane.backend.core.model.request.OutcomeRequest;
import app.butakane.backend.core.service.WalletService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wallet")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @GetMapping("/info")
    public ResponseEntity<?> getWalletInfo(HttpServletRequest request) {
        return walletService.getWalletInfo(request);
    }

    @PutMapping("/income")
    public ResponseEntity<?> income(HttpServletRequest request, @RequestBody IncomeRequest payload) {
        return walletService.addIncome(request, payload);
    }

    @PutMapping("/outcome")
    public ResponseEntity<?> outcome(HttpServletRequest request, @RequestBody OutcomeRequest payload) {
        return walletService.addOutcome(request, payload);
    }
}

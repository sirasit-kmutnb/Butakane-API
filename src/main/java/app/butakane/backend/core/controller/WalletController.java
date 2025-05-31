package app.butakane.backend.core.controller;

import app.butakane.backend.core.model.request.IncomeRequest;
import app.butakane.backend.core.model.request.OutcomeRequest;
import app.butakane.backend.core.model.response.WalletDataResponse;
import app.butakane.backend.core.service.WalletService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    @Autowired private WalletService walletService;

    @GetMapping
    public ResponseEntity<WalletDataResponse> getWallet(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(walletService.getWalletInfo(token));
    }
}


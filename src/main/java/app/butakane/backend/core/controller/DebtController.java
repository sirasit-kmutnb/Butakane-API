package app.butakane.backend.core.controller;

import app.butakane.backend.core.model.request.DebtActionRequest;
import app.butakane.backend.core.model.request.DebtResolveRequest;
import app.butakane.backend.core.model.response.DebtDataResponse;
import app.butakane.backend.core.model.response.MyDebtOverviewResponse;
import app.butakane.backend.core.service.DebtService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DebtController {

    @Autowired private DebtService debtService;

    @PostMapping("/borrow")
    public ResponseEntity<String> borrow(@RequestHeader("Authorization") String token,
                                         @RequestBody DebtActionRequest request) {
        debtService.recordBorrow(token, request);
        return ResponseEntity.ok("Borrow recorded.");
    }

    @PostMapping("/lend")
    public ResponseEntity<String> lend(@RequestHeader("Authorization") String token,
                                       @RequestBody DebtActionRequest request) {
        debtService.recordLend(token, request);
        return ResponseEntity.ok("Lend recorded.");
    }

    @PutMapping("/payback")
    public ResponseEntity<String> payBack(@RequestHeader("Authorization") String token,
                                          @RequestBody DebtResolveRequest request) {
        debtService.payBack(token, request);
        return ResponseEntity.ok("Debt has been paid back.");
    }

    @PutMapping("/receiveback")
    public ResponseEntity<String> receiveBack(@RequestHeader("Authorization") String token,
                                              @RequestBody DebtResolveRequest request) {
        debtService.receiveBack(token, request);
        return ResponseEntity.ok("Debt has been received back.");
    }

    @GetMapping("/overview")
    public ResponseEntity<List<DebtDataResponse>> getDebtData(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(debtService.getDebtData(token));
    }

    @GetMapping("/alldebtinfo")
    public ResponseEntity<MyDebtOverviewResponse> getMyDebtData(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(debtService.getMyDebtdata(token));
    }

}



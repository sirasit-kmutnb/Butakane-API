package app.butakane.backend.core.controller;

import app.butakane.backend.core.model.request.IncomeRequest;
import app.butakane.backend.core.model.response.MoneyDataResponse;
import app.butakane.backend.core.model.response.MoneySummaryResponse;
import app.butakane.backend.core.service.MoneyService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MoneyController {

    @Autowired private MoneyService moneyService;

    @PutMapping("/income")
    public ResponseEntity<String> income(
            @RequestHeader("Authorization") String token,
            @RequestBody IncomeRequest request) {

        moneyService.recordIncome(token, request);
        return ResponseEntity.ok("Income recorded successfully.");
    }

    @PutMapping("/outcome")
    public ResponseEntity<String> outcome(
            @RequestHeader("Authorization") String token,
            @RequestBody IncomeRequest request) {

        moneyService.recordOutcome(token, request);
        return ResponseEntity.ok("Outcome recorded successfully.");
    }

    @GetMapping("/all")
    public ResponseEntity<List<MoneyDataResponse>> getMoneyData(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(moneyService.getMoneyData(token));
    }

    @GetMapping("/incomesum")
    public ResponseEntity<Integer> getIncomeSummary(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(moneyService.getIncomeSummary(token));
    }

    @GetMapping("/outcomesum")
    public ResponseEntity<Integer> getOutcomeSummary(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(moneyService.getOutcomeSummary(token));
    }

}



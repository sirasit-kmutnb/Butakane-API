package app.butakane.backend.core.controller;

import app.butakane.backend.core.model.response.OverviewResponse;
import app.butakane.backend.core.service.OverviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/alloverviewinfo")
public class OverviewController {

    @Autowired private OverviewService overviewService;

    @GetMapping
    public ResponseEntity<OverviewResponse> getOverview(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(overviewService.getOverview(token));
    }
}


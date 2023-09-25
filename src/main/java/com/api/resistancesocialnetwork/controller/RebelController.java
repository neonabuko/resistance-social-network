package com.api.resistancesocialnetwork.controller;

import com.api.resistancesocialnetwork.request.RequestLocationUpdate;
import com.api.resistancesocialnetwork.request.RequestReport;
import com.api.resistancesocialnetwork.request.RequestTrade;
import com.api.resistancesocialnetwork.rules.TradeFailureException;
import com.api.resistancesocialnetwork.usecase.LocationUpdateUseCase;
import com.api.resistancesocialnetwork.usecase.ReportUseCase;
import com.api.resistancesocialnetwork.usecase.TradeUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rebel")
public class RebelController {
    private final ReportUseCase reportUseCase;
    private final LocationUpdateUseCase locationUpdateUseCase;
    private final TradeUseCase tradeUseCase;

    public RebelController(ReportUseCase reportUseCase, LocationUpdateUseCase locationUpdateUseCase, TradeUseCase tradeUseCase) {
        this.reportUseCase = reportUseCase;
        this.locationUpdateUseCase = locationUpdateUseCase;
        this.tradeUseCase = tradeUseCase;
    }

    @PatchMapping("/report")
    public ResponseEntity<String> handleReport(@RequestBody RequestReport requestReport) {
        reportUseCase.handle(requestReport.reportForm());
        return ResponseEntity.ok("Rebel reported.");
    }

    @PatchMapping("/update-location")
    public ResponseEntity<String> handleLocationUpdate(@RequestBody RequestLocationUpdate requestLocationUpdate) {
        locationUpdateUseCase.handle(requestLocationUpdate.locationUpdateForm());
        return ResponseEntity.ok("Location updated.");
    }

    @PatchMapping("/trade")
    public ResponseEntity<String> handleTrade(@RequestBody RequestTrade requestTrade) throws TradeFailureException {
        tradeUseCase.handle(requestTrade.tradeForm());
        return ResponseEntity.ok("Trade successful");
    }
}
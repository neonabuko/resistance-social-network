package com.api.resistancesocialnetwork.controller;

import com.api.resistancesocialnetwork.request.LocationUpdateRequest;
import com.api.resistancesocialnetwork.request.ReportRequest;
import com.api.resistancesocialnetwork.request.TradeRequest;
import com.api.resistancesocialnetwork.rules.commons.ResistanceSocialNetworkException;
import com.api.resistancesocialnetwork.usecase.LocationUpdateUseCase;
import com.api.resistancesocialnetwork.usecase.ReportUseCase;
import com.api.resistancesocialnetwork.usecase.TradeUseCase;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<String> handleReport(@RequestBody ReportRequest reportRequest) {
        reportUseCase.handle(reportRequest.report());
        return new ResponseEntity<>(HttpStatus.valueOf(200));
    }

    @PatchMapping("/update-location")
    public ResponseEntity<String> handleLocationUpdate(@RequestBody LocationUpdateRequest locationUpdateRequest) throws ResistanceSocialNetworkException {
        locationUpdateUseCase.handle(locationUpdateRequest.locationUpdate());
        return new ResponseEntity<>(HttpStatus.valueOf(200));
    }

    @PatchMapping("/trade")
    public ResponseEntity<String> handleTrade(@RequestBody TradeRequest tradeRequest) throws ResistanceSocialNetworkException {
        tradeUseCase.handle(tradeRequest.trade());
        return new ResponseEntity<>(HttpStatus.valueOf(200));
    }
}
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
    private final ReportUseCase report;
    private final LocationUpdateUseCase locationUpdate;
    private final TradeUseCase trade;

    public RebelController(ReportUseCase report,
                           LocationUpdateUseCase locationUpdate,
                           TradeUseCase trade) {
        this.report = report;
        this.locationUpdate = locationUpdate;
        this.trade = trade;
    }

    @PatchMapping("/report")
    public ResponseEntity<String> report(@RequestBody ReportRequest report) {
        this.report.handle(report.report());
        return new ResponseEntity<>(HttpStatus.valueOf(200));
    }

    @PatchMapping("/update-location")
    public ResponseEntity<String> updateLocation(@RequestBody LocationUpdateRequest locationUpdate) throws ResistanceSocialNetworkException {
        this.locationUpdate.handle(locationUpdate.locationUpdate());
        return new ResponseEntity<>(HttpStatus.valueOf(200));
    }

    @PatchMapping("/trade")
    public ResponseEntity<String> trade(@RequestBody TradeRequest trade) throws ResistanceSocialNetworkException {
        this.trade.handle(trade.trade());
        return new ResponseEntity<>(HttpStatus.valueOf(200));
    }
}
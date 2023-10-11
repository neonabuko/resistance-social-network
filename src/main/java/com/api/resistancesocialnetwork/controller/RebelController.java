package com.api.resistancesocialnetwork.controller;

import com.api.resistancesocialnetwork.facade.LocationUpdateFacade;
import com.api.resistancesocialnetwork.facade.ReportFacade;
import com.api.resistancesocialnetwork.facade.TradeFacade;
import com.api.resistancesocialnetwork.rules.commons.ResistanceSocialNetworkException;
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
    public ResponseEntity<String> report(@RequestBody ReportFacade report) {
        this.report.handle(report);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/update-location")
    public ResponseEntity<String> updateLocation(@RequestBody LocationUpdateFacade locationUpdate) throws ResistanceSocialNetworkException {
        this.locationUpdate.handle(locationUpdate);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/trade")
    public ResponseEntity<String> trade(@RequestBody TradeFacade trade) throws ResistanceSocialNetworkException {
        this.trade.handle(trade);
        return ResponseEntity.ok().build();
    }
}
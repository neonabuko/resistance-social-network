package com.api.resistancesocialnetwork.controller;

import com.api.resistancesocialnetwork.model.Item;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
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
    public ResponseEntity<String> handleReport(@RequestBody RequestReport requestReport) throws Exception {
        reportUseCase.handle(requestReport.sourceId(), requestReport.targetId());
        return ResponseEntity.ok("Rebel reported.");
    }

    @PatchMapping("/location-update")
    public ResponseEntity<String> handleLocationUpdate(@RequestBody RequestLocationUpdate requestLocationUpdate) {
        Double newLatitude = requestLocationUpdate.latitude();
        Double newLongitude = requestLocationUpdate.longitude();
        String newBase = requestLocationUpdate.base();

        locationUpdateUseCase.handle(requestLocationUpdate.locationId(), newLatitude, newLongitude, newBase);
        return ResponseEntity.ok("Location updated.");
    }

    @PatchMapping("/trade")
    public ResponseEntity<String> handleTrade(@RequestBody RequestTrade requestTrade) throws TradeFailureException {
        Integer sourceInventoryId = requestTrade.sourceInventoryId();
        Item sourceTradeItem = requestTrade.sourceTradeItem();
        Integer targetInventoryId = requestTrade.targetInventoryId();
        Item targetTradeItem = requestTrade.targetTradeItem();

        tradeUseCase.handle(sourceInventoryId, sourceTradeItem, targetInventoryId, targetTradeItem);
        return ResponseEntity.ok("Trade successful");
    }
}

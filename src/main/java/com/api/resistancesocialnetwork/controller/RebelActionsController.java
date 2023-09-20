package com.api.resistancesocialnetwork.controller;

import com.api.resistancesocialnetwork.repositories.InventoryRepository;
import com.api.resistancesocialnetwork.repositories.LocationRepository;
import com.api.resistancesocialnetwork.repositories.RebelRepository;
import com.api.resistancesocialnetwork.request.RequestReport;
import com.api.resistancesocialnetwork.usecase.LocationUpdateUseCase;
import com.api.resistancesocialnetwork.usecase.ReportUseCase;
import com.api.resistancesocialnetwork.usecase.TradeUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RebelActionsController {
    private final RebelRepository rebelRepo;
    private final LocationRepository locationRepo;
    private final InventoryRepository inventoryRepo;
    private final ReportUseCase reportUseCase;
    private final LocationUpdateUseCase locationUpdateUseCase;
    private final TradeUseCase tradeUseCase;

    @Autowired
    public RebelActionsController(RebelRepository rebelRepo, LocationRepository locationRepo, InventoryRepository inventoryRepo, ReportUseCase reportUseCase, LocationUpdateUseCase locationUpdateUseCase, TradeUseCase tradeUseCase) {
        this.rebelRepo = rebelRepo;
        this.locationRepo = locationRepo;
        this.inventoryRepo = inventoryRepo;
        this.reportUseCase = reportUseCase;
        this.locationUpdateUseCase = locationUpdateUseCase;
        this.tradeUseCase = tradeUseCase;
    }


    @PatchMapping("/report")
    public ResponseEntity<String> handleReport(@RequestBody RequestReport requestReport) {
        try {
            reportUseCase.handle(requestReport.sourceId(), requestReport.targetId());
        } catch (Exception e) {
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Reported rebel " + ". reportedId= " + requestReport.targetId());
    }

//    @PatchMapping("/locationUpdate")
//    public ResponseEntity<String> handleLocationUpdate(@RequestBody RequestLocationUpdate requestLocationUpdate) {
//        Integer locationId;
//        try {
//            locationId = locationRepo
//                    .findById(rebelRepo.findByName(requestLocationUpdate.rebelName()).orElseThrow().getId())
//                    .orElseThrow()
//                    .getOwnerId();
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("no such rebel");
//        }
//        String response = locationUpdateUseCase.handle(locationId, requestLocationUpdate.newLocation());
//        return ResponseEntity.ok(response);
//    }

//    @PatchMapping("/trade")
//    public ResponseEntity<String> handleTrade(@RequestBody RequestTrade requestTrade) {
//        try {
//            tradeUseCase.handle(requestTrade.sourceInventoryId(), requestTrade.sourceTradeItem(),
//                    requestTrade.targetInventoryId(), requestTrade.targetTradeItem());
//        } catch (TradeFailureException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//        return ResponseEntity.ok("Trade successful");
//    }
}

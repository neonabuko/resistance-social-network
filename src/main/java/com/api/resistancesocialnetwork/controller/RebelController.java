package com.api.resistancesocialnetwork.controller;

import com.api.resistancesocialnetwork.entity.User;
import com.api.resistancesocialnetwork.facade.DeleteUserFacade;
import com.api.resistancesocialnetwork.facade.LocationUpdateFacade;
import com.api.resistancesocialnetwork.facade.ReportFacade;
import com.api.resistancesocialnetwork.facade.TradeFacade;
import com.api.resistancesocialnetwork.rules.commons.ResistanceSocialNetworkException;
import com.api.resistancesocialnetwork.usecase.DeleteUserUseCase;
import com.api.resistancesocialnetwork.usecase.LocationUpdateUseCase;
import com.api.resistancesocialnetwork.usecase.ReportUseCase;
import com.api.resistancesocialnetwork.usecase.TradeUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rebel")
public class RebelController {
    private final ReportUseCase report;
    private final LocationUpdateUseCase locationUpdate;
    private final TradeUseCase trade;
    private final DeleteUserUseCase delete;

    public RebelController(ReportUseCase report,
                           LocationUpdateUseCase locationUpdate,
                           TradeUseCase trade, DeleteUserUseCase delete) {
        this.report = report;
        this.locationUpdate = locationUpdate;
        this.trade = trade;
        this.delete = delete;
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

    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@RequestBody DeleteUserFacade deleteUserFacade,
                                         @RequestHeader("Authorization") String header) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        this.delete.handle(deleteUserFacade, user);
        return ResponseEntity.ok().build();
    }
}
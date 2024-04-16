package com.api.resistancesocialnetwork.controller;

import com.api.resistancesocialnetwork.entity.ResistanceUser;
import com.api.resistancesocialnetwork.facade.DeleteUserFacade;
import com.api.resistancesocialnetwork.facade.ReportFacade;
import com.api.resistancesocialnetwork.facade.TradeFacade;
import com.api.resistancesocialnetwork.facade.UpdateLocationFacade;
import com.api.resistancesocialnetwork.rules.commons.ResistanceException;
import com.api.resistancesocialnetwork.usecase.DeleteUserUseCase;
import com.api.resistancesocialnetwork.usecase.ReportUseCase;
import com.api.resistancesocialnetwork.usecase.TradeUseCase;
import com.api.resistancesocialnetwork.usecase.UpdateLocationUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rebel")
public class RebelController {
    private final ReportUseCase report;
    private final UpdateLocationUseCase locationUpdate;
    private final TradeUseCase trade;
    private final DeleteUserUseCase delete;

    public RebelController(ReportUseCase report,
                           UpdateLocationUseCase updateLocation,
                           TradeUseCase trade, DeleteUserUseCase delete) {
        this.report = report;
        this.locationUpdate = updateLocation;
        this.trade = trade;
        this.delete = delete;
    }

    @PatchMapping("/report")
    public ResponseEntity<String> handleReport(@RequestBody ReportFacade facade) {
        report.handle(facade);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/update-location")
    public ResponseEntity<String> handleUpdateLocation(@RequestBody UpdateLocationFacade facade) throws ResistanceException {
        locationUpdate.handle(facade);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/trade")
    public ResponseEntity<String> handleTrade(@RequestBody TradeFacade facade) throws ResistanceException {
        trade.handle(facade);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> handleDelete(@RequestBody DeleteUserFacade facade,
                                               @RequestHeader("Authorization") String header) {
        ResistanceUser user = (ResistanceUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        delete.handle(facade, user);
        return ResponseEntity.ok().build();
    }
}
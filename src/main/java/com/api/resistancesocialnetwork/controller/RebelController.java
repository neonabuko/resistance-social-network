package com.api.resistancesocialnetwork.controller;

import com.api.resistancesocialnetwork.entity.User;
import com.api.resistancesocialnetwork.facade.DeleteUserFacade;
import com.api.resistancesocialnetwork.facade.UpdateLocationFacade;
import com.api.resistancesocialnetwork.facade.ReportFacade;
import com.api.resistancesocialnetwork.facade.TradeFacade;
import com.api.resistancesocialnetwork.rules.commons.ResistanceException;
import com.api.resistancesocialnetwork.usecase.DeleteUserUseCase;
import com.api.resistancesocialnetwork.usecase.UpdateLocationUseCase;
import com.api.resistancesocialnetwork.usecase.ReportUseCase;
import com.api.resistancesocialnetwork.usecase.TradeUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rebel")
@Tag(name = "Rebel Controller", description = "Controller para as ações do rebelde")
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
    @Operation(description = "Reporta outro rebelde como traidor", method = "PATCH")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rebelde reportado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos/rebelde não encontrado"),
            @ApiResponse(responseCode = "403", description = "Conta não encontrada/usuário não autenticado"),
            @ApiResponse(responseCode = "405", description = "Método não permitido")
    })
    public ResponseEntity<Void> handleReport(@RequestBody ReportFacade facade) {
        report.handle(facade);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/update-location")
    @Operation(description = "Atualiza a localização de um rebelde", method = "PATCH")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Localização atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos/rebelde não encontrado"),
            @ApiResponse(responseCode = "403", description = "Conta não encontrada/usuário não autenticado"),
            @ApiResponse(responseCode = "405", description = "Método não permitido")
    })
    public ResponseEntity<Void> handleUpdateLocation(@RequestBody UpdateLocationFacade facade) throws ResistanceException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer id = user.getId();
        locationUpdate.handle(facade, user);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/trade")
    @Operation(description = "Negocia com outro rebelde", method = "PATCH")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Negociação realizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos/itens não encontrados nos inventários/pontos não batem"),
            @ApiResponse(responseCode = "403", description = "Conta não encontrada/usuário não autenticado"),
            @ApiResponse(responseCode = "405", description = "Método não permitido")
    })
    public ResponseEntity<Void> handleTrade(@RequestBody TradeFacade facade) throws ResistanceException {
        trade.handle(facade);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete")
    @Operation(description = "Deleta um usuário", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário deletado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos/usuário não encontrado"),
            @ApiResponse(responseCode = "403", description = "Conta não encontrada/usuário não autenticado"),
            @ApiResponse(responseCode = "405", description = "Método não permitido")
    })
    public ResponseEntity<Void> handleDelete(@RequestBody DeleteUserFacade facade,
                                               @RequestHeader("Authorization") String header) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        delete.handle(facade, user);
        return ResponseEntity.ok().build();
    }
}
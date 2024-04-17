package com.api.resistancesocialnetwork.controller;

import com.api.resistancesocialnetwork.entity.Rebel;
import com.api.resistancesocialnetwork.facade.stats.AlliesTraitorsPercentagesFacade;
import com.api.resistancesocialnetwork.facade.stats.ItemAverageFacade;
import com.api.resistancesocialnetwork.rules.commons.ResistanceException;
import com.api.resistancesocialnetwork.usecase.statistics.AlliesTraitorsPercentagesUseCase;
import com.api.resistancesocialnetwork.usecase.statistics.AlliesUseCase;
import com.api.resistancesocialnetwork.usecase.statistics.ItemAveragesUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/stats")
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Statistics Controller", description = "Controller para mostrar dados da aliança")
public class StatisticsController {
    private final AlliesUseCase allies;
    private final AlliesTraitorsPercentagesUseCase alliesTraitorsPercentages;
    private final ItemAveragesUseCase itemAverages;

    public StatisticsController(AlliesUseCase allies,
                                AlliesTraitorsPercentagesUseCase alliesTraitorsPercentages,
                                ItemAveragesUseCase itemAverages) {
        this.allies = allies;
        this.alliesTraitorsPercentages = alliesTraitorsPercentages;
        this.itemAverages = itemAverages;
    }

    @GetMapping("/allies")
    @Operation(description = "Mostra todos os aliados", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mostrou todos os aliados"),
            @ApiResponse(responseCode = "204", description = "Não há aliados para mostrar"),
            @ApiResponse(responseCode = "403", description = "Usuário não autenticado"),
            @ApiResponse(responseCode = "405", description = "Método não permitido")
    })
    public ResponseEntity<List<Rebel>> allies() {
        List<Rebel> allies = this.allies.handle();
        if (allies.isEmpty()) return ResponseEntity.status(204).build();
        return ResponseEntity.ok().body(allies);
    }

    @GetMapping("/allies-traitors-percentages")
    @Operation(description = "Mostra a porcentagem de aliados vs. traidores", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mostrou as porcentagens"),
            @ApiResponse(responseCode = "204", description = "Não há porcentages para mostrar"),
            @ApiResponse(responseCode = "403", description = "Usuário não autenticado"),
            @ApiResponse(responseCode = "405", description = "Método não permitido")
    })
    public ResponseEntity<AlliesTraitorsPercentagesFacade> alliesTraitorsPercentages() {
        AlliesTraitorsPercentagesFacade percentages;
        try {
            percentages = alliesTraitorsPercentages.handle();
        } catch (ResistanceException e) {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.ok().body(percentages);
    }

    @GetMapping("/item-averages")
    @Operation(description = "Mostra a média de itens por rebelde", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mostrou as médias"),
            @ApiResponse(responseCode = "204", description = "Não há médias para mostrar"),
            @ApiResponse(responseCode = "403", description = "Usuário não autenticado"),
            @ApiResponse(responseCode = "405", description = "Método não permitido")
    })
    public ResponseEntity<ItemAverageFacade> itemAverages() {
        var averages = itemAverages.handle();
        return ResponseEntity.ok().body(averages);
    }
}
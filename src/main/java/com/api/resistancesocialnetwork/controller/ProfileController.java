package com.api.resistancesocialnetwork.controller;

import com.api.resistancesocialnetwork.entity.User;
import com.api.resistancesocialnetwork.facade.ProfileFacade;
import com.api.resistancesocialnetwork.rules.commons.ResistanceException;
import com.api.resistancesocialnetwork.usecase.ProfileUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@Tag(name = "Profile Controller", description = "Controller para cadastro de dados do rebelde")
public class ProfileController {
    private final ProfileUseCase profile;

    public ProfileController(ProfileUseCase profile) {
        this.profile = profile;
    }

    @GetMapping("/")
    @Operation(summary = "Mostra a página principal", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Página acessada com sucesso"),
            @ApiResponse(responseCode = "405", description = "Método não permitido")
    })
    public ResponseEntity<Void> getHomePage() {
        return new ResponseEntity<>(HttpStatus.valueOf(200));
    }

    @PostMapping("/profile")
    @Operation(summary = "Faz o cadastro dos dados do rebelde", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Dados cadastrados com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "403", description = "Conta de usuário não encontrada"),
            @ApiResponse(responseCode = "405", description = "Método não permitido")
    })
    public ResponseEntity<Void> handleProfile(@RequestBody ProfileFacade signup,
                                              @RequestHeader("Authorization") String header) throws ResistanceException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        profile.handle(signup, user.getLogin());
        return ResponseEntity.created(URI.create("/profile")).build();
    }
}

package com.api.resistancesocialnetwork.controller;

import com.api.resistancesocialnetwork.facade.LoginFacade;
import com.api.resistancesocialnetwork.facade.SignupFacade;
import com.api.resistancesocialnetwork.usecase.LoginUseCase;
import com.api.resistancesocialnetwork.usecase.SignupUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Auth Controller", description = "Controller para signup e login")
public class AuthController {
    private final LoginUseCase login;
    private final SignupUseCase signup;

    public AuthController(LoginUseCase login, SignupUseCase signup) {
        this.login = login;
        this.signup = signup;
    }

    @PostMapping("/signup")
    @Operation(summary = "Faz o cadastro do usuário", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cadastro bem-sucedido"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "405", description = "Método não permitido")
    })
    public ResponseEntity<Void> handleSignup(@RequestBody SignupFacade facade) {
        signup.handle(facade);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    @Operation(summary = "Faz o login do usuário", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login bem-sucedido. Retorna um token", content = @Content()),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content()),
            @ApiResponse(responseCode = "403", description = "Conta não encontrada", content = @Content()),
            @ApiResponse(responseCode = "405", description = "Método não permitido", content = @Content()),
    })
    public ResponseEntity<String> handleLogin(@RequestBody LoginFacade facade) {
        var token = login.handle(facade);
        return ResponseEntity.ok().body(token);
    }
}

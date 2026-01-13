package com.esia.big_shop_backend.presentation.rest;

import com.esia.big_shop_backend.application.usecase.user.AssignRoleToUserUseCase;
import com.esia.big_shop_backend.application.usecase.user.command.AssignRoleCommand;
import com.esia.big_shop_backend.presentation.dto.request.user.AssignRoleRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@Tag(name = "Role", description = "Role management APIs")
public class RoleController {

    private final AssignRoleToUserUseCase assignRoleToUserUseCase;

    @PostMapping("/assign")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Assign role to user")
    public ResponseEntity<Void> assignRoleToUser(@RequestBody @Valid AssignRoleRequest request) {
        AssignRoleCommand command = new AssignRoleCommand(request.getUserId(), request.getRoleId());
        assignRoleToUserUseCase.execute(command);
        return ResponseEntity.ok().build();
    }
}

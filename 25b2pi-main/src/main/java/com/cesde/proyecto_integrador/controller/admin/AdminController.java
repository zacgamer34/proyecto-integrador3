package com.cesde.proyecto_integrador.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cesde.proyecto_integrador.dto.admin.UserRegisterDTO;
import com.cesde.proyecto_integrador.dto.admin.UserResponseDTO;
import com.cesde.proyecto_integrador.dto.admin.UserUpdateDTO;
import com.cesde.proyecto_integrador.model.User;
import com.cesde.proyecto_integrador.service.admin.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/admin/users")
@Tag(name = "Admin Controller", description = "Admin operations")
public class AdminController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Register a new user", description = "Creates a new user in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User successfully created", content = @Content(schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "409", description = "User already exists")
    })
    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> registerUser(
            @Parameter(description = "User details for registration", required = true) @RequestBody UserRegisterDTO user) {
        UserResponseDTO registeredUser = userService.register(user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(registeredUser);
    }

    @Operation(summary = "Change user password", description = "Changes a user's password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password changed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid password"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PutMapping("/{id}/password")
    public ResponseEntity<Void> changePassword(
            @Parameter(description = "ID of the user", required = true) @PathVariable Long id,
            @Parameter(description = "Current password", required = true) @RequestParam String oldPassword,
            @Parameter(description = "New password", required = true) @RequestParam String newPassword) {
        userService.changePassword(id, oldPassword, newPassword);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Reset user password", description = "Resets a user's password (admin only)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password reset successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "403", description = "Not authorized to reset password")
    })
    @PutMapping("/{id}/reset-password")
    public ResponseEntity<Void> resetPassword(
            @Parameter(description = "ID of the user", required = true) @PathVariable Long id,
            @Parameter(description = "New password", required = true) @RequestParam String newPassword) {
        userService.resetPassword(id, newPassword);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get all users", description = "Retrieves a list of all users in the system")
    @ApiResponse(responseCode = "200", description = "List of users retrieved successfully", content = @Content(schema = @Schema(implementation = User.class)))
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @Operation(summary = "Get user by ID", description = "Retrieves a user by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found", content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(
            @Parameter(description = "ID of the user to retrieve", required = true) @PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @Operation(summary = "Update user", description = "Updates an existing user's information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully", content = @Content(schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(
            @Parameter(description = "ID of the user to update", required = true) @PathVariable Long id,
            @Parameter(description = "Updated user details", required = true) @RequestBody UserUpdateDTO userUpdateDTO) {
        User updatedUser = userService.update(id, userUpdateDTO);
        return ResponseEntity.ok(UserResponseDTO.fromUser(updatedUser));
    }

    @Operation(summary = "Delete user", description = "Deletes a user from the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "ID of the user to delete", required = true) @PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get user by email", description = "Retrieves a user by their email address")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found", content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(
            @Parameter(description = "Email of the user to retrieve", required = true) @PathVariable String email) {
        return ResponseEntity.ok(userService.findByEmail(email));
    }

    @Operation(summary = "Get users by role", description = "Retrieves a list of users with a specific role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of users retrieved successfully", content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "400", description = "Invalid role")
    })
    @GetMapping("/role/{role}")
    public ResponseEntity<List<User>> getUsersByRole(
            @Parameter(description = "Role to filter users by", required = true) @PathVariable User.Role role) {
        return ResponseEntity.ok(userService.findByRole(role));
    }

}
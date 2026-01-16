package de.unibayreuth.se.taskboard.api.controller;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import de.unibayreuth.se.taskboard.api.mapper.UserDtoMapper;
import de.unibayreuth.se.taskboard.api.dtos.UserDto;

import de.unibayreuth.se.taskboard.business.ports.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import de.unibayreuth.se.taskboard.business.domain.User;
import java.util.stream.Collectors;


import java.util.List;
import java.util.Optional;
import java.util.UUID;



@OpenAPIDefinition(
        info = @Info(
                title = "TaskBoard",
                version = "0.0.1"
        )
)
@Tag(name = "Users")
@Controller
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;
     private final UserDtoMapper userDtoMapper;

    // TODO: Add GET /api/users endpoint to retrieve all users.
    @GetMapping
     public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> dtos = userService.getAllUsers()
                .stream()
                .map(userDtoMapper::fromBusiness)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // TODO: Add GET /api/users/{id} endpoint to retrieve a user by ID.
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String id) {
        Optional<User> user = userService.getUserById(java.util.UUID.fromString(id));
        return user
            .map(userDtoMapper::fromBusiness)      
            .map(ResponseEntity::ok)               
            .orElseGet(() -> ResponseEntity.notFound().build());   
    }

    // TODO: Add POST /api/users endpoint to create a new user based on a provided user DTO.
    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto dto) {
        User user = new User(dto.getName());
        User created = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userDtoMapper.fromBusiness(created));
    }

    //added this to delete a user by id
    @DeleteMapping("/{id}")
        @ResponseStatus(HttpStatus.NO_CONTENT) 
        public void deleteUser(@PathVariable String id) {
        UUID uuid = UUID.fromString(id);
        userService.deleteUser(uuid); 
}
}

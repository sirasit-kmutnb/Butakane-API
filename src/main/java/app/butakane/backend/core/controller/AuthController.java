package app.butakane.backend.core.controller;

import app.butakane.backend.core.model.request.AuthRequest;
import app.butakane.backend.core.model.response.AuthResponse;
import app.butakane.backend.core.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/reg")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody AuthRequest request) {
        return userService.registerUser(request);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        return userService.loginUser(request);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestHeader("Authorization") String token) {
        return userService.deleteUser(token);
    }
}

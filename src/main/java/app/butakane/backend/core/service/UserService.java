package app.butakane.backend.core.service;

import app.butakane.backend.core.model.User;
import app.butakane.backend.core.model.request.AuthRequest;
import app.butakane.backend.core.model.response.AuthResponse;
import app.butakane.backend.core.repository.UserRepository;
import app.butakane.backend.core.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public ResponseEntity<AuthResponse> registerUser(AuthRequest request) {
        if (!request.getPassword().equals(request.getConfirmPass())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new AuthResponse("Password doesn't match", null, null));
        }

        if (userRepository.existsByUsername(request.getUsername().toLowerCase())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new AuthResponse("Username has been used", null, null));
        }

        User user = new User();
        user.setUsername(request.getUsername().toLowerCase());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);

        String token = jwtUtil.generateToken(user);
        return ResponseEntity.ok(new AuthResponse("User registered successfully", user.getUsername(), token));
    }

    public ResponseEntity<AuthResponse> loginUser(AuthRequest request) {
        Optional<User> userOpt = userRepository.findByUsername(request.getUsername().toLowerCase());

        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new AuthResponse("Username doesn't exist", null, null));
        }

        User user = userOpt.get();
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new AuthResponse("Password doesn't match", null, null));
        }

        String token = jwtUtil.generateToken(user);
        return ResponseEntity.ok(new AuthResponse("Login successful", user.getUsername(), token));
    }

    public ResponseEntity<?> deleteUser(String token) {
        String userId = jwtUtil.extractUserId(token);
        userRepository.deleteById(userId);
        return ResponseEntity.ok("User deleted successfully");
    }
}

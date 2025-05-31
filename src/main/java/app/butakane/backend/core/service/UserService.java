package app.butakane.backend.core.service;

import app.butakane.backend.core.model.User;
import app.butakane.backend.core.model.request.AuthRequest;
import app.butakane.backend.core.model.response.AuthResponse;
import app.butakane.backend.core.repository.*;
import app.butakane.backend.core.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private BorrowRepository borrowRepository;

    @Autowired
    private LendRepository lendRepository;

    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthResponse register(AuthRequest request) {
        String username = request.getUsername().toLowerCase();
        String password = request.getPassword();
        String confirmPassword = request.getConfirmPass();

        if (!password.equalsIgnoreCase(confirmPassword)) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        String id = UUID.randomUUID().toString();
        String hashed = new BCryptPasswordEncoder().encode(password);
        userRepository.createUser(id, username, hashed);

        walletRepository.createBase(id);
        borrowRepository.createBase(id);
        lendRepository.createBase(id);
        goalRepository.createBase(id);

        return new AuthResponse("New user has been created.", username, null);
    }

    public AuthResponse login(AuthRequest request) {
        var user = userRepository.findByUsername(request.getUsername().toLowerCase());
        String hashed = (String) user.get("password");

        if (!new BCryptPasswordEncoder().matches(request.getPassword(), hashed)) {
            throw new IllegalArgumentException("Invalid password");
        }

        String token = jwtUtil.generateToken(user.get("id").toString(), user.get("username").toString());
        return new AuthResponse("Login successful", user.get("username").toString(), token);
    }
}


package friends_auto_mobile.controller;

import friends_auto_mobile.entity.User;
import friends_auto_mobile.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class LoginController {

    @Autowired
    private UserRepository repository;

    @PostMapping("/login")
    public User login(
            @RequestBody User request) {

        return repository
                .findByUsernameAndPassword(
                        request.getUsername(),
                        request.getPassword()
                )
                .orElseThrow(
                        () -> new RuntimeException(
                                "Invalid Credentials"
                        )
                );
    }
}
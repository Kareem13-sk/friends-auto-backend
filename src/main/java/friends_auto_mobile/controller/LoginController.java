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

    @GetMapping("/test-users")
    public Object testUsers() {
        return repository.findAll();
    }

    @GetMapping("/seed-users")
    public String seedUsers() {

        if (repository.count() > 0) {
            return "Users already exist";
        }

        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword("admin123");
        admin.setRole("ADMIN");
        repository.save(admin);

        User naimulla = new User();
        naimulla.setUsername("naimulla");
        naimulla.setPassword("shop123");
        naimulla.setRole("ADMIN");
        repository.save(naimulla);

        User staff = new User();
        staff.setUsername("staff1");
        staff.setPassword("staff123");
        staff.setRole("STAFF");
        repository.save(staff);

        return "Users Added Successfully";
    }
}
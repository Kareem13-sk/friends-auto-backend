package friends_auto_mobile.repository;

import friends_auto_mobile.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository
        extends JpaRepository<User, Long> {

    Optional<User>
    findByUsernameAndPassword(
            String username,
            String password
    );
}
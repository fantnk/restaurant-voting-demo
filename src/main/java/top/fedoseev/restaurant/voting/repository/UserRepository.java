package top.fedoseev.restaurant.voting.repository;

import org.springframework.transaction.annotation.Transactional;
import top.fedoseev.restaurant.voting.model.User;

import java.util.Optional;

@Transactional(readOnly = true)
public interface UserRepository extends BaseRepository<User> {
    Optional<User> getByEmail(String email);
}

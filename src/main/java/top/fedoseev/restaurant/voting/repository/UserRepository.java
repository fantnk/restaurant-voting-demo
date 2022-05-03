package top.fedoseev.restaurant.voting.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;
import top.fedoseev.restaurant.voting.model.User;

import java.util.Optional;

@Transactional(readOnly = true)
public interface UserRepository extends BaseRepository<User>, JpaSpecificationExecutor<User> {
    Optional<User> getByEmail(String email);
}

package top.fedoseev.restaurant.voting.service;

import org.springframework.lang.Nullable;
import top.fedoseev.restaurant.voting.to.user.UserCreationByAdminRequest;
import top.fedoseev.restaurant.voting.to.user.UserFullResponse;
import top.fedoseev.restaurant.voting.to.user.UserModificationByAdminRequest;

import java.util.List;

public interface UserAdminService {
    void update(int id, UserModificationByAdminRequest request);

    UserFullResponse create(UserCreationByAdminRequest request);

    void delete(int id);

    UserFullResponse getByEmail(String email);

    List<UserFullResponse> findAll();

    UserFullResponse getById(int id);

    void particleUpdate(int id, @Nullable Boolean enabled, @Nullable String password);
}

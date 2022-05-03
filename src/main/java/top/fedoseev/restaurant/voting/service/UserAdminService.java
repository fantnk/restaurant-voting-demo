package top.fedoseev.restaurant.voting.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import top.fedoseev.restaurant.voting.to.user.UserCreationByAdminRequest;
import top.fedoseev.restaurant.voting.to.user.UserFilter;
import top.fedoseev.restaurant.voting.to.user.UserFullResponse;
import top.fedoseev.restaurant.voting.to.user.UserModificationByAdminRequest;

public interface UserAdminService {
    void update(int id, UserModificationByAdminRequest request);

    UserFullResponse create(UserCreationByAdminRequest request);

    void delete(int id);

    UserFullResponse getByEmail(String email);

    Page<UserFullResponse> findAll(Pageable pageable, @Nullable UserFilter filter);

    UserFullResponse getById(int id);

    void particleUpdate(int id, @Nullable Boolean enabled, @Nullable String password);
}

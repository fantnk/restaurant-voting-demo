package top.fedoseev.restaurant.voting.service;

import top.fedoseev.restaurant.voting.to.user.UserCreationRequest;
import top.fedoseev.restaurant.voting.to.user.UserFullResponse;
import top.fedoseev.restaurant.voting.to.user.UserModificationRequest;
import top.fedoseev.restaurant.voting.to.user.UserResponse;

public interface UserService {

    void update(UserModificationRequest request);

    UserResponse create(UserCreationRequest request);

    void delete(int id);

    UserFullResponse getById(int id);

}

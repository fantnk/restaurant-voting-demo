package top.fedoseev.restaurant.voting.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import top.fedoseev.restaurant.voting.config.MapStructConfig;
import top.fedoseev.restaurant.voting.model.User;
import top.fedoseev.restaurant.voting.to.user.UserCreationByAdminRequest;
import top.fedoseev.restaurant.voting.to.user.UserCreationRequest;
import top.fedoseev.restaurant.voting.to.user.UserFullResponse;
import top.fedoseev.restaurant.voting.to.user.UserModificationByAdminRequest;
import top.fedoseev.restaurant.voting.to.user.UserModificationRequest;
import top.fedoseev.restaurant.voting.to.user.UserResponse;

@Mapper(config = MapStructConfig.class)
public abstract class UserMapper {

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Mapping(target = "email", expression = "java(request.email().toLowerCase())")
    public abstract User fromModificationRequest(UserModificationRequest request);

    @Mapping(target = "email", expression = "java(request.email().toLowerCase())")
    public abstract User fromModificationByAdminRequest(UserModificationByAdminRequest request);

    @Mapping(target = "password", expression = "java(passwordEncoder.encode(request.password()))")
    @Mapping(target = "email", expression = "java(request.email().toLowerCase())")
    public abstract User fromCreationRequest(UserCreationRequest request);

    @Mapping(target = "password", expression = "java(passwordEncoder.encode(request.password()))")
    @Mapping(target = "email", expression = "java(request.email().toLowerCase())")
    public abstract User fromCreationByAdminRequest(UserCreationByAdminRequest request);

    public abstract UserResponse toUserResponse(User user);

    public abstract UserFullResponse toUserFullResponse(User user);
}

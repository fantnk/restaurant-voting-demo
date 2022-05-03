package top.fedoseev.restaurant.voting.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.fedoseev.restaurant.voting.exception.ErrorMessage;
import top.fedoseev.restaurant.voting.exception.NotFoundException;
import top.fedoseev.restaurant.voting.mapper.UserMapper;
import top.fedoseev.restaurant.voting.model.User;
import top.fedoseev.restaurant.voting.repository.UserRepository;
import top.fedoseev.restaurant.voting.to.user.UserCreationByAdminRequest;
import top.fedoseev.restaurant.voting.to.user.UserCreationRequest;
import top.fedoseev.restaurant.voting.to.user.UserFullResponse;
import top.fedoseev.restaurant.voting.to.user.UserModificationByAdminRequest;
import top.fedoseev.restaurant.voting.to.user.UserModificationRequest;
import top.fedoseev.restaurant.voting.to.user.UserResponse;
import top.fedoseev.restaurant.voting.web.AuthUser;

import java.util.List;
import java.util.Optional;

@Service
@CacheConfig(cacheNames = UserServiceImpl.CACHE_NAME)
@RequiredArgsConstructor
public class UserServiceImpl implements UserAdminService, UserService {
    static final String CACHE_NAME = "users";

    private final UserRepository repository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Cacheable
    @Override
    public UserFullResponse getByEmail(String email) {
        return repository.getByEmail(email)
                .map(userMapper::toUserFullResponse)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.USER_NOT_FOUND, "email", email));
    }

    @Cacheable
    @Override
    public List<UserFullResponse> findAll() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "name", "email")).stream()
                .map(userMapper::toUserFullResponse)
                .toList();
    }

    @Cacheable
    @Override
    public UserFullResponse getById(int id) {
        return userMapper.toUserFullResponse(get(id));
    }

    @Transactional
    @CacheEvict(allEntries = true)
    @Override
    public void particleUpdate(int id, @Nullable Boolean enabled, @Nullable String password) {
        User user = get(id);
        if (enabled != null) {
            user.setEnabled(enabled);
        }
        if (StringUtils.isNotEmpty(password)) {
            user.setPassword(passwordEncoder.encode(password));
        }
        repository.save(user);

    }

    @Transactional
    @CacheEvict(allEntries = true)
    @Override
    public void update(int id, UserModificationByAdminRequest request) {
        User user = userMapper.fromModificationByAdminRequest(request);
        user.setId(id);
        repository.save(user);
    }

    @Transactional
    @CacheEvict(allEntries = true)
    @Override
    public UserFullResponse create(UserCreationByAdminRequest request) {
        User user = userMapper.fromCreationByAdminRequest(request);
        User createdUser = repository.save(user);
        return userMapper.toUserFullResponse(createdUser);
    }

    @Transactional
    @CacheEvict(allEntries = true)
    @Override
    public void delete(int id) {
        repository.deleteExisted(id);
    }

    @Transactional
    @CacheEvict(allEntries = true)
    @Override
    public void update(UserModificationRequest request) {
        User user = userMapper.fromModificationRequest(request);
        user.setId(getAuthenticatedUser().id());
        repository.save(user);
    }

    @Transactional
    @CacheEvict(allEntries = true)
    @Override
    public UserResponse create(UserCreationRequest request) {
        User user = userMapper.fromCreationRequest(request);
        User savedUser = repository.save(user);
        return userMapper.toUserResponse(savedUser);
    }

    private User get(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.USER_NOT_FOUND, "id", id));
    }

    private AuthUser getAuthenticatedUser() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(Authentication::getPrincipal)
                .filter(AuthUser.class::isInstance)
                .map(AuthUser.class::cast)
                .orElseThrow(() -> new AuthenticationCredentialsNotFoundException("Could not find original Authentication object"));
    }
}

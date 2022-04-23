package top.fedoseev.restaurant.voting.web.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import top.fedoseev.restaurant.voting.service.UserService;
import top.fedoseev.restaurant.voting.to.user.UserCreationRequest;
import top.fedoseev.restaurant.voting.to.user.UserFullResponse;
import top.fedoseev.restaurant.voting.to.user.UserModificationRequest;
import top.fedoseev.restaurant.voting.to.user.UserResponse;
import top.fedoseev.restaurant.voting.web.AuthUser;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = ProfileController.REST_URL)
@RequiredArgsConstructor
@Tag(name = "profile", description = "the profile API")
public class ProfileController {
    static final String REST_URL = "/api/profile";

    private final UserService userService;

    @Operation(summary = "Get profile by ID")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public UserFullResponse get(@AuthenticationPrincipal AuthUser authUser) {
        return userService.getById(authUser.id());
    }

    @Operation(summary = "Delete profile by ID")
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal AuthUser authUser) {
        userService.delete(authUser.id());
    }

    @Operation(summary = "Register new profile")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserCreationRequest request) {
        UserResponse response = userService.create(request);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL).build().toUri();
        return ResponseEntity.created(uriOfNewResource).body(response);
    }

    @Operation(summary = "Modify existed profile")
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody @Valid UserModificationRequest request) {
        userService.update(request);
    }
}

package top.fedoseev.restaurant.voting.web.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import top.fedoseev.restaurant.voting.service.UserAdminService;
import top.fedoseev.restaurant.voting.to.user.UserCreationByAdminRequest;
import top.fedoseev.restaurant.voting.to.user.UserFilter;
import top.fedoseev.restaurant.voting.to.user.UserFullResponse;
import top.fedoseev.restaurant.voting.to.user.UserModificationByAdminRequest;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.net.URI;

@RestController
@RequestMapping(value = AdminUserController.REST_URL)
@RequiredArgsConstructor
@Tag(name = "user", description = "the user API")
public class AdminUserController {

    static final String REST_URL = "/api/admin/users";

    private final UserAdminService userService;

    @Operation(summary = "Get user by ID")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserFullResponse get(@PathVariable int id) {
        return userService.getById(id);
    }

    @Operation(summary = "Delete user by ID")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        userService.delete(id);
    }

    @Operation(summary = "Get all users")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<UserFullResponse> getAll(@PageableDefault(sort = {"name", "email"}) Pageable pageable, @Nullable UserFilter filter) {
        return userService.findAll(pageable, filter);
    }

    @Operation(summary = "Create new user")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserFullResponse> createWithLocation(@Valid @RequestBody UserCreationByAdminRequest request) {
        UserFullResponse created = userService.create(request);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.id())
                .toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @Operation(summary = "Modify existed user")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable int id, @Valid @RequestBody UserModificationByAdminRequest request) {
        userService.update(id, request);
    }

    @Operation(summary = "Get user by email")
    @GetMapping(value = "/by-email", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserFullResponse getByEmail(@RequestParam @Valid @Email String email) {
        return userService.getByEmail(email);
    }

    @Operation(summary = "Particle update existed user")
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void particleUpdate(@PathVariable int id, @RequestParam(required = false) Boolean enabled,
                               @RequestParam(required = false) @Size(min = 5, max = 32) String password) {
        userService.particleUpdate(id, enabled, password);
    }

}

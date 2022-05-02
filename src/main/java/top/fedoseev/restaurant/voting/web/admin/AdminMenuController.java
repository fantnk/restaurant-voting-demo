package top.fedoseev.restaurant.voting.web.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import top.fedoseev.restaurant.voting.service.MenuService;
import top.fedoseev.restaurant.voting.to.menu.MenuCreationRequest;
import top.fedoseev.restaurant.voting.to.menu.MenuResponse;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = AdminMenuController.REST_URL)
@RequiredArgsConstructor
@Tag(name = AdminMenuController.TAG_NAME, description = "the menu API")
public class AdminMenuController {
    static final String REST_URL = "/api/admin/restaurant/{restaurantId}/menu";
    static final String TAG_NAME = "menu";

    private final MenuService service;

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete menu by ID")
    public void delete(
            @PathVariable @Parameter(description = "ID of the menu that needs to be deleted", required = true, example = "1") int id,
            @PathVariable @Parameter(description = "ID of the restaurant", required = true, example = "1") int restaurantId) {
        service.delete(id, restaurantId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create menu")
    public ResponseEntity<MenuResponse> create(@Valid @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody
                                                       MenuCreationRequest request,
                                               @PathVariable @Parameter(description = "ID of the restaurant", required = true, example = "1")
                                                       int restaurantId) {
        MenuResponse created = service.create(request, restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(restaurantId, created.id())
                .toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

}

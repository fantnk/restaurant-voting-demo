package top.fedoseev.restaurant.voting.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
import java.util.List;

@RestController
@RequestMapping(value = MenuController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = MenuController.TAG_NAME, description = "the menu API")
public class MenuController {
    static final String REST_URL = "/api/admin/restaurant/{restaurantId}/menu";
    static final String TAG_NAME = "menu";

    private final MenuService service;

    @Operation(summary = "Get menu by ID")
    @GetMapping("/{id}")
    public MenuResponse get(
            @PathVariable @Parameter(description = "ID of the menu that needs to be fetched", required = true, example = "1") int id,
            @PathVariable @Parameter(description = "ID of the restaurant", required = true, example = "1") int restaurantId) {
        return service.getById(id, restaurantId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete menu by ID")
    public void delete(
            @PathVariable @Parameter(description = "ID of the menu that needs to be deleted", required = true, example = "1") int id,
            @PathVariable @Parameter(description = "ID of the restaurant", required = true, example = "1") int restaurantId) {
        service.delete(id, restaurantId);
    }

    @GetMapping
    @Operation(summary = "Get all menus")
    public List<MenuResponse> getAll(
            @PathVariable @Parameter(description = "ID of the restaurant", required = true, example = "1") int restaurantId) {
        return service.findAll(restaurantId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
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

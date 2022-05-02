package top.fedoseev.restaurant.voting.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.fedoseev.restaurant.voting.service.MenuService;
import top.fedoseev.restaurant.voting.to.menu.MenuResponse;

import java.util.List;

@RestController
@RequestMapping(value = MenuController.REST_URL)
@RequiredArgsConstructor
@Tag(name = MenuController.TAG_NAME, description = "the menu API")
public class MenuController {
    static final String REST_URL = "/api/restaurant/{restaurantId}/menu";
    static final String TAG_NAME = "menu";

    private final MenuService service;

    @Operation(summary = "Get menu by ID")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public MenuResponse get(
            @PathVariable @Parameter(description = "ID of the menu that needs to be fetched", required = true, example = "1") int id,
            @PathVariable @Parameter(description = "ID of the restaurant", required = true, example = "1") int restaurantId) {
        return service.getById(id, restaurantId);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get all menus")
    public List<MenuResponse> getAll(
            @PathVariable @Parameter(description = "ID of the restaurant", required = true, example = "1") int restaurantId) {
        return service.findAll(restaurantId);
    }

}

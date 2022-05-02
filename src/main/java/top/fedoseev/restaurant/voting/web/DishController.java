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
import top.fedoseev.restaurant.voting.service.DishService;
import top.fedoseev.restaurant.voting.to.dish.DishResponse;

import java.util.List;

@RestController
@RequestMapping(value = DishController.REST_URL)
@RequiredArgsConstructor
@Tag(name = DishController.TAG_NAME, description = "the dish API")
public class DishController {
    static final String REST_URL = "/api/restaurant/{restaurantId}/menu/{menuId}/dish";
    static final String TAG_NAME = "dish";

    private final DishService service;

    @Operation(summary = "Get dish by ID")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public DishResponse get(
            @PathVariable @Parameter(description = "ID of the dish that needs to be fetched", required = true, example = "1") int id,
            @PathVariable @Parameter(description = "ID of the menu", required = true, example = "1") int menuId,
            @PathVariable @Parameter(description = "ID of the restaurant", required = true, example = "1") int restaurantId) {
        return service.getById(id, menuId, restaurantId);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get all dishes")
    public List<DishResponse> getAll(
            @PathVariable @Parameter(description = "ID of the menu", required = true, example = "1") int menuId,
            @PathVariable @Parameter(description = "ID of the restaurant", required = true, example = "1") int restaurantId) {
        return service.findAll(menuId, restaurantId);
    }

}

package top.fedoseev.restaurant.voting.service;

import top.fedoseev.restaurant.voting.to.dish.DishCreationRequest;
import top.fedoseev.restaurant.voting.to.dish.DishResponse;

import java.util.List;

public interface DishService {
    DishResponse getById(int id, int menuId, int restaurantId);

    DishResponse create(DishCreationRequest request, int menuId, int restaurantId);

    List<DishResponse> findAll(int menuId, int restaurantId);

    void delete(int id, int menuId, int restaurantId);
}

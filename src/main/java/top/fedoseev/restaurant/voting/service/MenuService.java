package top.fedoseev.restaurant.voting.service;

import top.fedoseev.restaurant.voting.to.menu.MenuCreationRequest;
import top.fedoseev.restaurant.voting.to.menu.MenuModificationRequest;
import top.fedoseev.restaurant.voting.to.menu.MenuResponse;

import java.util.List;

public interface MenuService {
    MenuResponse getById(int id, int restaurantId);

    MenuResponse create(MenuCreationRequest request, int restaurantId);

    List<MenuResponse> findAll(int restaurantId);

    void delete(int id, int restaurantId);

    MenuResponse update(MenuModificationRequest request, int id, int restaurantId);
}

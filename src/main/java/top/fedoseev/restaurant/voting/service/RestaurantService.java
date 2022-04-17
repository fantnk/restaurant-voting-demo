package top.fedoseev.restaurant.voting.service;

import top.fedoseev.restaurant.voting.to.restaurant.RestaurantCreationRequest;
import top.fedoseev.restaurant.voting.to.restaurant.RestaurantResponse;

import java.util.List;

public interface RestaurantService {
    RestaurantResponse getById(int id);

    RestaurantResponse create(RestaurantCreationRequest request);

    List<RestaurantResponse> findAll();

    void delete(int id);
}

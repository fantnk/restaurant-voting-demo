package top.fedoseev.restaurant.voting.service;

import top.fedoseev.restaurant.voting.to.RestaurantCreationRequest;
import top.fedoseev.restaurant.voting.to.RestaurantResponse;

import java.util.List;

public interface RestaurantService {
    RestaurantResponse getById(Integer id);

    RestaurantResponse create(RestaurantCreationRequest request);

    List<RestaurantResponse> findAll();

    void delete(Integer id);
}

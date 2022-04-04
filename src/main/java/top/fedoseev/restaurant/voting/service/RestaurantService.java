package top.fedoseev.restaurant.voting.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.fedoseev.restaurant.voting.exception.EntityNotFoundException;
import top.fedoseev.restaurant.voting.exception.ErrorMessage;
import top.fedoseev.restaurant.voting.mapper.RestaurantMapper;
import top.fedoseev.restaurant.voting.model.Restaurant;
import top.fedoseev.restaurant.voting.repository.RestaurantRepository;
import top.fedoseev.restaurant.voting.to.RestaurantCreationRequest;
import top.fedoseev.restaurant.voting.to.RestaurantResponse;

import java.util.List;

@Service
@CacheConfig(cacheNames = RestaurantService.CACHE_NAME)
@RequiredArgsConstructor
public class RestaurantService {
    static final String CACHE_NAME = "restaurants";

    private final RestaurantRepository repository;
    private final RestaurantMapper restaurantMapper;

    @Cacheable
    public RestaurantResponse getById(Integer id) {
        return repository.findById(id)
                .map(restaurantMapper::toRestaurantResponse)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.RESTAURANT_NOT_FOUND, "id", id));
    }

    @Transactional
    @CacheEvict(value = CACHE_NAME, allEntries = true)
    public RestaurantResponse create(RestaurantCreationRequest request) {
        Restaurant restaurant = restaurantMapper.fromCreationRequest(request);
        Restaurant savedRestaurant = repository.save(restaurant);
        return restaurantMapper.toRestaurantResponse(savedRestaurant);
    }

    @Cacheable
    public List<RestaurantResponse> findAll() {
        return repository.findAll().stream()
                .map(restaurantMapper::toRestaurantResponse)
                .toList();
    }

    @Transactional
    @CacheEvict(value = CACHE_NAME, allEntries = true)
    public void delete(Integer id) {
        repository.deleteExisted(id);
    }

}

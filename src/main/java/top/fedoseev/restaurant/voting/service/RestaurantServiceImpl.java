package top.fedoseev.restaurant.voting.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.fedoseev.restaurant.voting.exception.ErrorMessage;
import top.fedoseev.restaurant.voting.exception.NotFoundException;
import top.fedoseev.restaurant.voting.mapper.RestaurantMapper;
import top.fedoseev.restaurant.voting.model.Restaurant;
import top.fedoseev.restaurant.voting.repository.RestaurantRepository;
import top.fedoseev.restaurant.voting.to.restaurant.RestaurantCreationRequest;
import top.fedoseev.restaurant.voting.to.restaurant.RestaurantResponse;

import java.util.List;

@Service
@CacheConfig(cacheNames = RestaurantServiceImpl.CACHE_NAME)
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {
    static final String CACHE_NAME = "restaurants";

    private final RestaurantRepository repository;
    private final RestaurantMapper restaurantMapper;

    @Override
    @Cacheable
    public RestaurantResponse getById(int id) {
        return repository.findById(id)
                .map(restaurantMapper::toRestaurantResponse)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.NOT_FOUND_BY_ID, "Restaurant", id));
    }

    @Override
    @Transactional
    @CacheEvict(value = CACHE_NAME, allEntries = true)
    public RestaurantResponse create(RestaurantCreationRequest request) {
        Restaurant restaurant = restaurantMapper.fromCreationRequest(request);
        Restaurant savedRestaurant = repository.save(restaurant);
        return restaurantMapper.toRestaurantResponse(savedRestaurant);
    }

    @Override
    @Cacheable
    public List<RestaurantResponse> findAll() {
        return repository.findAll().stream()
                .map(restaurantMapper::toRestaurantResponse)
                .toList();
    }

    @Override
    @Transactional
    @CacheEvict(value = CACHE_NAME, allEntries = true)
    public void delete(int id) {
        repository.deleteExisted(id);
    }

}

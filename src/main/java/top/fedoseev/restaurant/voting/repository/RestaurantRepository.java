package top.fedoseev.restaurant.voting.repository;

import org.springframework.transaction.annotation.Transactional;
import top.fedoseev.restaurant.voting.model.Restaurant;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {

}

package top.fedoseev.restaurant.voting.mapper;

import org.mapstruct.Mapper;
import top.fedoseev.restaurant.voting.config.MapStructConfig;
import top.fedoseev.restaurant.voting.model.Restaurant;
import top.fedoseev.restaurant.voting.to.restaurant.RestaurantCreationRequest;
import top.fedoseev.restaurant.voting.to.restaurant.RestaurantResponse;

@Mapper(config = MapStructConfig.class)
public interface RestaurantMapper {

    RestaurantResponse toRestaurantResponse(Restaurant restaurant, Integer totalVotesToday);

    Restaurant fromCreationRequest(RestaurantCreationRequest request);
}

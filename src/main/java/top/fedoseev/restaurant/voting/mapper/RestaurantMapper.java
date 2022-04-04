package top.fedoseev.restaurant.voting.mapper;

import org.mapstruct.Mapper;
import top.fedoseev.restaurant.voting.config.MapStructConfig;
import top.fedoseev.restaurant.voting.model.Restaurant;
import top.fedoseev.restaurant.voting.to.RestaurantCreationRequest;
import top.fedoseev.restaurant.voting.to.RestaurantResponse;

@Mapper(config = MapStructConfig.class)
public interface RestaurantMapper {

    RestaurantResponse toRestaurantResponse(Restaurant restaurant);

    Restaurant fromCreationRequest(RestaurantCreationRequest request);
}

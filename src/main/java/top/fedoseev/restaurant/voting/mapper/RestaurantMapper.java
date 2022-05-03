package top.fedoseev.restaurant.voting.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import top.fedoseev.restaurant.voting.config.MapStructConfig;
import top.fedoseev.restaurant.voting.model.Restaurant;
import top.fedoseev.restaurant.voting.to.restaurant.RestaurantCreationRequest;
import top.fedoseev.restaurant.voting.to.restaurant.RestaurantModificationRequest;
import top.fedoseev.restaurant.voting.to.restaurant.RestaurantResponse;

@Mapper(config = MapStructConfig.class)
public interface RestaurantMapper {

    RestaurantResponse toRestaurantResponse(Restaurant restaurant, Integer totalVotesToday);

    @Mapping(target = "menus", ignore = true)
    @Mapping(target = "id", ignore = true)
    Restaurant fromCreationRequest(RestaurantCreationRequest request);

    @Mapping(target = "menus", ignore = true)
    @Mapping(target = "id", ignore = true)
    void updateFromModificationRequest(@MappingTarget Restaurant restaurant, RestaurantModificationRequest request);
}

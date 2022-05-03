package top.fedoseev.restaurant.voting.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import top.fedoseev.restaurant.voting.config.MapStructConfig;
import top.fedoseev.restaurant.voting.model.Dish;
import top.fedoseev.restaurant.voting.model.Menu;
import top.fedoseev.restaurant.voting.to.dish.DishCreationRequest;
import top.fedoseev.restaurant.voting.to.dish.DishModificationRequest;
import top.fedoseev.restaurant.voting.to.dish.DishResponse;

@Mapper(uses = MenuMapper.class, config = MapStructConfig.class)
public interface DishMapper {

    DishResponse toDishResponse(Dish dish);

    @Mapping(target = "menu", source = "menu")
    @Mapping(target = "id", ignore = true)
    Dish fromCreationRequest(DishCreationRequest request, Menu menu);

    @Mapping(target = "menu", ignore = true)
    @Mapping(target = "id", ignore = true)
    void updateFromModificationRequest(@MappingTarget Dish dish, DishModificationRequest request);
}

package top.fedoseev.restaurant.voting.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import top.fedoseev.restaurant.voting.config.MapStructConfig;
import top.fedoseev.restaurant.voting.model.Menu;
import top.fedoseev.restaurant.voting.model.Restaurant;
import top.fedoseev.restaurant.voting.to.menu.MenuCreationRequest;
import top.fedoseev.restaurant.voting.to.menu.MenuResponse;

@Mapper(config = MapStructConfig.class)
public interface MenuMapper {

    MenuResponse toMenuResponse(Menu menu);

    @Mapping(target = "restaurant", source = "restaurant")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dishes", ignore = true)
    Menu fromCreationRequest(MenuCreationRequest request, Restaurant restaurant);
}

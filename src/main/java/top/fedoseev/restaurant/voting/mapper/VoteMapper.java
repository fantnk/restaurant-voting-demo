package top.fedoseev.restaurant.voting.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import top.fedoseev.restaurant.voting.config.MapStructConfig;
import top.fedoseev.restaurant.voting.model.Vote;
import top.fedoseev.restaurant.voting.to.vote.VoteResponse;

@Mapper(config = MapStructConfig.class)
public interface VoteMapper {

    @Mapping(target = "restaurantId", source = "vote.restaurant.id")
    VoteResponse toVoteResponse(Vote vote);

}

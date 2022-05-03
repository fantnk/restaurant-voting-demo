package top.fedoseev.restaurant.voting.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import top.fedoseev.restaurant.voting.model.Vote;
import top.fedoseev.restaurant.voting.util.validation.ValidationUtil;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {

    @Query("select v from Vote v where v.date = current_date and v.user.id = :userId")
    Optional<Vote> findTodayVoteByUserId(int userId);

    Optional<Vote> findByIdAndUserId(int id, int userId);

    @Query("select count(v) from Vote v where v.date = current_date and v.restaurant.id = :restaurantId")
    int countTodayVotesByRestaurantId(int restaurantId);

    default Map<Integer, Integer> countTodayVotes() {
        return countTodayVotesInternal().stream()
                .collect(Collectors.toMap(o -> (Integer) o[0], o -> ((Long) o[1]).intValue()));
    }

    @Query("select v.restaurant.id, count(v.id) from Vote v where v.date = current_date group by v.restaurant")
    List<Object[]> countTodayVotesInternal();

    List<Vote> findAllByUserId(int userId);

    @Transactional
    int deleteByIdAndUserId(int id, int userId);

    default void deleteExistedByIdSAndUserId(int id, int userId){
        ValidationUtil.checkModification(deleteByIdAndUserId(id, userId), id);
    }
}

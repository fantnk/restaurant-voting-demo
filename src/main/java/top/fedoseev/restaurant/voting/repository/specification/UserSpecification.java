package top.fedoseev.restaurant.voting.repository.specification;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import top.fedoseev.restaurant.voting.model.NamedEntity_;
import top.fedoseev.restaurant.voting.model.User;
import top.fedoseev.restaurant.voting.model.User_;
import top.fedoseev.restaurant.voting.to.user.UserFilter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public record UserSpecification(@Nullable UserFilter filter) implements Specification<User> {

    @Override
    @Nullable
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Specification<User> specification = Specification.where(null);

        if (filter == null) {
            return specification.toPredicate(root, query, criteriaBuilder);
        }

        if (StringUtils.isNotEmpty(filter.email())) {
            specification = specification.and((r, q, b) -> b.like(root.get(User_.email), filter.email().replace("*", "%")));
        }

        if (StringUtils.isNotEmpty(filter.name())) {
            specification = specification.and((r, q, b) -> b.like(root.get(NamedEntity_.name), filter.name().replace("*", "%")));
        }

        return specification.toPredicate(root, query, criteriaBuilder);
    }
}

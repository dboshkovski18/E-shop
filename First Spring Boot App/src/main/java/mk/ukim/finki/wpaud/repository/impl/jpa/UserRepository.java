package mk.ukim.finki.wpaud.repository.impl.jpa;

import mk.ukim.finki.wpaud.model.User;
import mk.ukim.finki.wpaud.model.projections.UserProjection;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.Entity;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByUsernameAndPassword(String username, String password);

    Optional<User> findByUsername(String username);

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = {"carts"})
    @Query("select u from User u")
    List<User> fetchAll();

    @EntityGraph(type = EntityGraph.EntityGraphType.LOAD, attributePaths = {"shoppingCarts", "discount"})
    @Query("select u from User u")
    List<User> loadAll();

    @Query("select u.username, u.name, u.surname from User u")
    List<UserProjection> takeUsernameNameAndSurnameByProjection();


}

package mk.ukim.finki.wpaud.repository.impl.jpa;

import mk.ukim.finki.wpaud.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    List<Category> findByNameLike(String name);
    void deleteByName(String name);
}

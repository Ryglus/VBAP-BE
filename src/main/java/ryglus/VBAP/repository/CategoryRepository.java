package ryglus.VBAP.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ryglus.VBAP.model.Category;
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    // You can add custom query methods if needed
}

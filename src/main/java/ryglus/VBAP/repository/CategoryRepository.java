package ryglus.VBAP.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ryglus.VBAP.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    // You can add custom query methods if needed
}

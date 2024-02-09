package ryglus.VBAP.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ryglus.VBAP.model.Product;

import java.util.List;
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByPriceBetween(Double minPrice, Double maxPrice);
    List<Product> findByNameContaining(String name);
}
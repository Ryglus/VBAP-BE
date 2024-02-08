package ryglus.VBAP.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ryglus.VBAP.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
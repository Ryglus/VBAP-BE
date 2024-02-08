package ryglus.VBAP.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ryglus.VBAP.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
    // You can add custom query methods if needed
}

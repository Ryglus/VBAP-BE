package ryglus.VBAP.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ryglus.VBAP.model.CustomerOrder;
@Repository
public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {
    // You can add custom query methods if needed
}

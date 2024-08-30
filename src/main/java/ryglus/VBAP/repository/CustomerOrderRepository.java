package ryglus.VBAP.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ryglus.VBAP.model.CustomerOrder;

import java.util.List;

@Repository
public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {
    @Query("SELECT o FROM CustomerOrder o JOIN FETCH o.products WHERE o.customer.id = :customerId")
    List<CustomerOrder> findAllByCustomer_Id(@Param("customerId") Long customerId);
}

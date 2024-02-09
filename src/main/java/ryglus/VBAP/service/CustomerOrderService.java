package ryglus.VBAP.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ryglus.VBAP.model.Customer;
import ryglus.VBAP.model.CustomerOrder;
import ryglus.VBAP.repository.CustomerOrderRepository;
import ryglus.VBAP.repository.CustomerRepository;

import java.util.List;

@Service
public class CustomerOrderService {

    private final CustomerOrderRepository customerOrderRepository;
    private final CustomerRepository customerRepository;
    private final JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    public CustomerOrderService(CustomerOrderRepository customerOrderRepository, CustomerRepository customerRepository, JwtUserDetailsService jwtUserDetailsService) {
        this.customerOrderRepository = customerOrderRepository;
        this.customerRepository = customerRepository;
        this.jwtUserDetailsService = jwtUserDetailsService;
    }

    public CustomerOrder createOrder(CustomerOrder order) {
        return customerOrderRepository.save(order);
    }

    public List<CustomerOrder> getOrdersByCustomerId(Long customerId) {
        return customerOrderRepository.findAllByCustomer_Id(customerId);
    }

    public CustomerOrder getOrderById(Long orderId) {
        return customerOrderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public Customer extractAppUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return (Customer) jwtUserDetailsService.loadUserByUsername(username);
    }
}
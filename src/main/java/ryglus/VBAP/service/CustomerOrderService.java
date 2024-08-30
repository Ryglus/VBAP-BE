package ryglus.VBAP.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ryglus.VBAP.DTO.orders.OrderRequestDTO;
import ryglus.VBAP.model.Customer;
import ryglus.VBAP.model.CustomerOrder;
import ryglus.VBAP.model.Product;
import ryglus.VBAP.repository.CustomerOrderRepository;
import ryglus.VBAP.repository.CustomerRepository;
import ryglus.VBAP.repository.ProductRepository;

import java.util.Date;
import java.util.List;

@Service
public class CustomerOrderService {

    private final CustomerOrderRepository customerOrderRepository;
    private final JwtUserDetailsService jwtUserDetailsService;
    private final ProductRepository productRepository;

    @Autowired
    public CustomerOrderService(CustomerOrderRepository customerOrderRepository, CustomerRepository customerRepository, JwtUserDetailsService jwtUserDetailsService, ProductRepository productRepository) {
        this.customerOrderRepository = customerOrderRepository;
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.productRepository = productRepository;
    }

    public CustomerOrder createOrder(OrderRequestDTO orderRequest) {
        // Extract the current user (customer) from the security context
        Customer customer = extractAppUser();

        // Create a new CustomerOrder entity from the DTO
        CustomerOrder newOrder = new CustomerOrder();
        newOrder.setOrderDate(new Date());
        newOrder.setStatus("PENDING");
        newOrder.setCustomer(customer);

        // Fetch products by their IDs provided in the DTO
        List<Product> products = productRepository.findAllById(orderRequest.getProductIds());
        newOrder.setProducts(products);

        // Save the new order
        return customerOrderRepository.save(newOrder);
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
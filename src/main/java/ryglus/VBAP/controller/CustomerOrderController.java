package ryglus.VBAP.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ryglus.VBAP.DTO.orders.CustomerOrderDTO;
import ryglus.VBAP.DTO.orders.OrderRequestDTO;
import ryglus.VBAP.DTO.products.ProductDTO;
import ryglus.VBAP.model.Customer;
import ryglus.VBAP.model.CustomerOrder;
import ryglus.VBAP.repository.ProductRepository;
import ryglus.VBAP.service.CustomerOrderService;
import ryglus.VBAP.service.JwtUserDetailsService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/orders")
public class CustomerOrderController {

    private final CustomerOrderService customerOrderService;

    @Autowired
    public CustomerOrderController(CustomerOrderService customerOrderService, JwtUserDetailsService jwtUserDetailsService, ProductRepository productRepository) {
        this.customerOrderService = customerOrderService;
    }

    @PostMapping
    public ResponseEntity<CustomerOrderDTO> createOrder(@RequestBody OrderRequestDTO orderRequest) {
        // Create a new order using the service layer
        CustomerOrder newOrder = customerOrderService.createOrder(orderRequest);

        // Convert CustomerOrder to CustomerOrderDTO
        CustomerOrderDTO orderDTO = new CustomerOrderDTO(
                newOrder.getId(),
                newOrder.getOrderDate(),
                newOrder.getStatus(),
                newOrder.getProducts().stream().map(product -> new ProductDTO(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice(),
                        product.isAvailable()
                )).collect(Collectors.toList())
        );

        // Return the DTO
        return ResponseEntity.ok(orderDTO);
    }


    @GetMapping
    public ResponseEntity<List<CustomerOrderDTO>> getOrders() {
        Customer customer = customerOrderService.extractAppUser();
        Long customerId = customer.getId();
        List<CustomerOrder> orders = customerOrderService.getOrdersByCustomerId(customerId);

        // Convert List of CustomerOrder to List of CustomerOrderDTO
        List<CustomerOrderDTO> orderDTOs = orders.stream().map(order -> {
            List<ProductDTO> productDTOs = order.getProducts().stream().map(product -> new ProductDTO(
                    product.getId(), product.getName(), product.getDescription(), product.getPrice(), product.isAvailable()
            )).collect(Collectors.toList());

            return new CustomerOrderDTO(order.getId(), order.getOrderDate(), order.getStatus(), productDTOs);
        }).collect(Collectors.toList());

        return ResponseEntity.ok(orderDTOs);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<CustomerOrderDTO> getOrderById(@PathVariable Long orderId) {
        CustomerOrder order = customerOrderService.getOrderById(orderId);

        // Convert CustomerOrder to CustomerOrderDTO
        List<ProductDTO> productDTOs = order.getProducts().stream().map(product -> new ProductDTO(
                product.getId(), product.getName(), product.getDescription(), product.getPrice(), product.isAvailable()
        )).collect(Collectors.toList());

        CustomerOrderDTO orderDTO = new CustomerOrderDTO(order.getId(), order.getOrderDate(), order.getStatus(), productDTOs);
        return ResponseEntity.ok(orderDTO);
    }


}
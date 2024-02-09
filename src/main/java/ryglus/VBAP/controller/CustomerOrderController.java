package ryglus.VBAP.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ryglus.VBAP.model.Customer;
import ryglus.VBAP.model.CustomerOrder;
import ryglus.VBAP.service.CustomerOrderService;
import ryglus.VBAP.service.JwtUserDetailsService;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/orders")
public class CustomerOrderController {

    private final CustomerOrderService customerOrderService;
    private final JwtUserDetailsService jwtUserDetailsService;
    @Autowired
    public CustomerOrderController(CustomerOrderService customerOrderService, JwtUserDetailsService jwtUserDetailsService) {
        this.customerOrderService = customerOrderService;
        this.jwtUserDetailsService = jwtUserDetailsService;
    }

    @PostMapping
    public ResponseEntity<CustomerOrder> createOrder(@RequestBody CustomerOrder order) {
        return ResponseEntity.ok(customerOrderService.createOrder(order));
    }

    @GetMapping
    public ResponseEntity<List<CustomerOrder>> getOrders() {
        Customer customer = customerOrderService.extractAppUser();
        Long customerId = customer.getId();
        return ResponseEntity.ok(customerOrderService.getOrdersByCustomerId(customerId));
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<CustomerOrder> getOrderById(@PathVariable Long orderId) {
        return ResponseEntity.ok(customerOrderService.getOrderById(orderId));
    }


}
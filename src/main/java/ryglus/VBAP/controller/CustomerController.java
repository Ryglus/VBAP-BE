package ryglus.VBAP.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ryglus.VBAP.DTO.customers.CustomerDTO;
import ryglus.VBAP.DTO.user.CustomerLoginRequestDto;
import ryglus.VBAP.DTO.user.CustomerLoginResponseDto;
import ryglus.VBAP.DTO.user.CustomerRegisterRequestDto;
import ryglus.VBAP.DTO.user.CustomerRegisterResponseDto;
import ryglus.VBAP.model.Customer;
import ryglus.VBAP.service.CustomerService;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class CustomerController {
    private final CustomerService appUserService;

    @Autowired
    public CustomerController(CustomerService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping("/me")
    @Operation(summary = "Get info about account", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<CustomerDTO> getCustomerInfo() {
        Customer authenticatedCustomer = (Customer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return ResponseEntity.ok(appUserService.whoAmI(authenticatedCustomer));
    }
    @PostMapping("/signup")
    public ResponseEntity<CustomerRegisterResponseDto> register(@RequestBody CustomerRegisterRequestDto appUserRegisterRequestDto) {
        return ResponseEntity.ok(appUserService.register(appUserRegisterRequestDto));
    }

    @PostMapping("/signin")
    public ResponseEntity<CustomerLoginResponseDto> login(@RequestBody CustomerLoginRequestDto appUserLoginRequestDto) {
        return ResponseEntity.ok(appUserService.login(appUserLoginRequestDto));
    }

    @PostMapping("/logout")
    public ResponseEntity<Boolean> logout(@RequestBody CustomerLoginRequestDto appUserLoginRequestDto) {
        return ResponseEntity.ok(appUserService.logout(appUserLoginRequestDto));
    }
}